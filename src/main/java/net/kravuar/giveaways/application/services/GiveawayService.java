package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.repo.GiveawayRepository;
import net.kravuar.giveaways.domain.dto.GiveawayFormDTO;
import net.kravuar.giveaways.domain.exceptions.GiveawayExhaustedException;
import net.kravuar.giveaways.domain.exceptions.GiveawayIsPrivateException;
import net.kravuar.giveaways.domain.exceptions.ResourceNotFoundException;
import net.kravuar.giveaways.domain.messages.GiveawayCounterDecreaseMessage;
import net.kravuar.giveaways.domain.messages.GiveawayMessage;
import net.kravuar.giveaways.domain.messages.UserSuccessfullyCollectedGiveawayMessage;
import net.kravuar.giveaways.domain.model.Giveaway;
import net.kravuar.giveaways.domain.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class GiveawayService {
    private final MessageService messageService;
    private final DestinationsProps destinationsProps;
    private final GiveawayRepository giveawayRepository;
    private final UserService userService;

    public Collection<Giveaway> findVisible(String username, Pageable pageable) {
        var user = userService.findByUsernameOrElseThrow(username);

        var subscribedTo = user.getSubscriptions().stream().map(User::getId).toList();
        return giveawayRepository
                .findAllByIsPrivateIsFalseOrOwnerIdIsIn(subscribedTo, pageable)
                .getContent();
    }

    public Giveaway findById(String giveawayId) {
        return giveawayRepository.findById(giveawayId).orElse(null);
    }

    public Giveaway findByIdOrElseThrow(String giveawayId) {
        var giveaway = findById(giveawayId);
        if (giveaway == null)
            throw new ResourceNotFoundException("giveaway", giveawayId);
        return giveaway;
    }

    public void addByUser(GiveawayFormDTO giveawayFormDTO, String username) {
        var totalCost = giveawayFormDTO.getAmount() * giveawayFormDTO.getUsages();
        userService.updateBalance(username, -totalCost);

        var giveaway = new Giveaway(userService.findByUsername(username), giveawayFormDTO);
        giveaway = giveawayRepository.save(giveaway);

        if (giveaway.getIsPrivate())
            messageService.sendToUser(
                    username,
                    destinationsProps.giveawayAdded,
                    new GiveawayMessage(giveaway)
            );
        else
            messageService.send(
                    destinationsProps.giveawayAdded,
                    new GiveawayMessage(giveaway)
            );
    }

    public void collectByUser(String giveawayId, String username) {
        var giveaway = findByIdOrElseThrow(giveawayId);
        var user = userService.findByUsernameOrElseThrow(username);

        if (giveaway.getUsagesLeft() > 0)
            if (!giveaway.getIsPrivate() || giveaway.getOwner().getSubscribers().contains(user)) {
                giveaway.setUsagesLeft(giveaway.getUsagesLeft() - 1);
                giveaway.getCollected().add(user);
                user.setBalance(user.getBalance() + giveaway.getAmount());
                messageService.send(
                        destinationsProps.giveawayCollected,
                        new GiveawayCounterDecreaseMessage(giveawayId)
                );
                messageService.sendToUser(
                        username,
                        destinationsProps.notifications,
                        new UserSuccessfullyCollectedGiveawayMessage(giveawayId)
                );
            }
            else
                throw new GiveawayIsPrivateException();
        else
            throw new GiveawayExhaustedException();
    }
}
