package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.repo.GiveawayRepository;
import net.kravuar.giveaways.domain.dto.NewGiveawayDTO;
import net.kravuar.giveaways.domain.exceptions.GiveawayExhaustedException;
import net.kravuar.giveaways.domain.exceptions.GiveawayIsPrivateException;
import net.kravuar.giveaways.domain.exceptions.ResourceNotFoundException;
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
    private final MessagesService messagesService;
    private final DestinationsProps destinationsProps;
    private final GiveawayRepository giveawayRepository;
    private final UserService userService;

    public Collection<Giveaway> findVisible(String username, Pageable pageable) {
        var user = userService.getByUsername(username);

        if (user == null)
            throw new ResourceNotFoundException(username, "user", username);

        var subscribedTo = user.getSubscriptions().stream().map(User::getId).toList();
        return giveawayRepository
                .findAllByIsPrivateIsFalseOrOwnerIdIsIn(subscribedTo, pageable)
                .getContent();
    }

    public Giveaway findById(String giveawayId) {
        return giveawayRepository.findById(giveawayId).orElse(null);
    }

    public void tryAddByUser(NewGiveawayDTO newGiveawayDTO, String username) {
        var user = userService.getByUsername(username);

        if (user == null)
            throw new ResourceNotFoundException(username, "user", username);

        var totalCost = newGiveawayDTO.getAmount() * newGiveawayDTO.getCount();
        userService.updateBalance(user, -totalCost);

        var giveaway = new Giveaway(user, newGiveawayDTO);
        giveaway = giveawayRepository.save(giveaway);

        if (giveaway.getIsPrivate())
            messagesService.sendNewPrivateGiveaway(giveaway, destinationsProps.giveawayAdded);
        else
            messagesService.sendNewPublicGiveaway(giveaway, destinationsProps.giveawayAdded);
    }

    public void tryConsumeByUser(String giveawayId, String username) {
        var giveaway = findById(giveawayId);
        var user = userService.getByUsername(username);

        if (giveaway == null)
            throw new ResourceNotFoundException(username, "giveaway", giveawayId);

        if (user == null)
            throw new ResourceNotFoundException(username, "user", username);

        if (giveaway.getCount() > 0)
            if (!giveaway.getIsPrivate() || giveaway.getOwner().getSubscribers().contains(user)) {
                giveaway.setCount(giveaway.getCount() - 1);
                user.setBalance(user.getBalance() + giveaway.getAmount());
                messagesService.sendCounterDecrease(giveawayId, destinationsProps.giveawayConsumed);
                messagesService.sendUserSuccessfullyConsumedGiveaway(username, giveawayId, destinationsProps.giveawayConsumed);
            }
            else
                throw new GiveawayIsPrivateException(username);
        else
            throw new GiveawayExhaustedException(username);
    }
}
