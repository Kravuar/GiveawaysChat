package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.repo.GiveawayRepository;
import net.kravuar.giveaways.domain.dto.GiveawayFormDTO;
import net.kravuar.giveaways.domain.exceptions.GiveawayExhaustedException;
import net.kravuar.giveaways.domain.exceptions.GiveawayIsPrivateException;
import net.kravuar.giveaways.domain.messages.GiveawayCounterDecreaseMessage;
import net.kravuar.giveaways.domain.messages.GiveawayMessage;
import net.kravuar.giveaways.domain.messages.UserSuccessfullyCollectedGiveawayMessage;
import net.kravuar.giveaways.domain.model.Giveaway;
import net.kravuar.giveaways.domain.model.Subscription;
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

    public Collection<Giveaway> findVisible(String userId, Pageable pageable) {
        var user = userService.findByIdOrElseThrow(userId);

        var subscribedTo = user.getSubscriptions().stream()
                .map(Subscription::getSubscriber)
                .map(User::getId)
                .toList();
        return giveawayRepository
                .findAllByIsPrivateIsFalseOrOwnerIdIsIn(subscribedTo, pageable)
                .getContent();
    }

    public void addByUser(GiveawayFormDTO giveawayFormDTO, String userId) {
        var totalCost = giveawayFormDTO.getAmount() * giveawayFormDTO.getUsages();
        userService.updateBalance(userId, -totalCost);

        var giveaway = new Giveaway(userService.getReferenceById(userId), giveawayFormDTO);
        giveaway = giveawayRepository.save(giveaway);

        if (giveaway.getIsPrivate())
            messageService.sendToUser(
                    userId,
                    destinationsProps.getGiveawayAdded(),
                    new GiveawayMessage(giveaway)
            );
        else
            messageService.send(
                    destinationsProps.getGiveawayAdded(),
                    new GiveawayMessage(giveaway)
            );
    }

    public void collectByUser(String giveawayId, String userId) {
        var giveaway = giveawayRepository.getReferenceById(giveawayId);
        var user = userService.findByIdOrElseThrow(userId);

        if (giveaway.getUsagesLeft() > 0)
            if (!giveaway.getIsPrivate() || user.getSubscriptions().stream().anyMatch(subscription -> subscription.getSubscripted().equals(giveaway.getOwner()))) {
                giveaway.setUsagesLeft(giveaway.getUsagesLeft() - 1);
                giveaway.getCollected().add(user);
                user.setBalance(user.getBalance() + giveaway.getAmount());
                messageService.send(
                        destinationsProps.getGiveawayCollected(),
                        new GiveawayCounterDecreaseMessage(giveawayId)
                );
                messageService.sendToUser(
                        userId,
                        destinationsProps.getNotifications(),
                        new UserSuccessfullyCollectedGiveawayMessage(giveawayId)
                );
            }
            else
                throw new GiveawayIsPrivateException();
        else
            throw new GiveawayExhaustedException();
    }
}
