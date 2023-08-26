package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.repo.GiveawayRepository;
import net.kravuar.giveaways.domain.dto.GiveawayFormDTO;
import net.kravuar.giveaways.domain.events.GiveawayCollected;
import net.kravuar.giveaways.domain.events.GiveawayPublished;
import net.kravuar.giveaways.domain.exceptions.GiveawayExhaustedException;
import net.kravuar.giveaways.domain.exceptions.GiveawayIsPrivateException;
import net.kravuar.giveaways.domain.model.giveaways.Giveaway;
import net.kravuar.giveaways.domain.model.user.User;
import net.kravuar.giveaways.domain.model.user.subscription.Subscription;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class GiveawayService {
    private final ApplicationEventPublisher publisher;
    private final GiveawayRepository giveawayRepository;
    private final UserService userService;

    public Collection<Giveaway> findVisible(String userId, Pageable pageable) {
        var user = userService.findById(userId);

        var subscribedTo = user != null
                ? user.getSubscriptions().stream()
                    .map(Subscription::getSubscriber)
                    .map(User::getId)
                    .toList()
                : Collections.<String>emptySet(); // Surely this won't affect query performance
        return giveawayRepository
                .findAllByIsPrivateIsFalseOrOwnerIdIsIn(subscribedTo, pageable)
                .getContent();
    }

    public void addByUser(GiveawayFormDTO giveawayFormDTO, String userId) {
        var totalCost = giveawayFormDTO.getAmount() * giveawayFormDTO.getUsages();
        userService.updateBalance(userId, -totalCost);

        var giveaway = new Giveaway(userService.getReferenceById(userId), giveawayFormDTO);
        giveaway = giveawayRepository.save(giveaway);

        publisher.publishEvent(new GiveawayPublished(giveaway));
    }

    public void collectByUser(String giveawayId, String userId) {
        var giveaway = giveawayRepository.getReferenceById(giveawayId);
        var user = userService.findByIdOrElseThrow(userId);

        if (giveaway.getUsagesLeft() > 0)
            if (!giveaway.getIsPrivate() || user.getSubscriptions().stream().anyMatch(subscription -> subscription.getSubscripted().equals(giveaway.getOwner()))) {
                giveaway.setUsagesLeft(giveaway.getUsagesLeft() - 1);
                giveaway.getCollected().add(user);
                user.setBalance(user.getBalance() + giveaway.getAmount());
                publisher.publishEvent(new GiveawayCollected(userId, giveawayId));
            } else
                throw new GiveawayIsPrivateException(giveawayId);
        else
            throw new GiveawayExhaustedException(giveawayId);
    }
}
