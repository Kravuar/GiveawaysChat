package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.SubscriptionProps;
import net.kravuar.giveaways.application.repo.SubscriptionRepo;
import net.kravuar.giveaways.domain.events.Subscribed;
import net.kravuar.giveaways.domain.events.Unsubscribed;
import net.kravuar.giveaways.domain.exceptions.InsufficientFundsException;
import net.kravuar.giveaways.domain.model.user.subscription.Subscription;
import net.kravuar.giveaways.domain.model.user.subscription.SubscriptionId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService {
    private final ApplicationEventPublisher publisher;
    private final SubscriptionRepo subscriptionRepo;
    private final SubscriptionProps subscriptionProps;
    private final UserService userService;

    @Scheduled(cron = "0 0 */3 * * *")
    public void subscriptionsExpirationHandling() {
        var now = ZonedDateTime.now();

        for (var subscription : subscriptionRepo.findAll())
            if (subscription.getExpirationTime().isBefore(now)) {
                var subscriberId = subscription.getSubscriber().getId();
                var subscriptedId = subscription.getSubscripted().getId();
                try {
                    subscribe(subscriberId, subscriptedId, 1);
                } catch (InsufficientFundsException exception) {
                    unsubscribe(subscriberId, subscriptedId);
                }
            }
    }

    public void subscribe(String subscriberId, String subscriptedId, Integer periods) {
        var subscriber = userService.getReferenceById(subscriberId);
        var subscripted = userService.getReferenceById(subscriptedId);

        var subscription = subscriptionRepo.findById(new SubscriptionId(subscriber, subscripted)).orElse(null);
        if (subscription != null) {
            var cost = subscription.getCost();
            subscriptionPayment(subscriberId, subscriptedId, cost * periods);

            subscription.setExpirationTime(
                    subscription.getExpirationTime()
                            .plusWeeks(subscriptionProps.getSubscriptionDuration())
            );
        }
        else {
            var cost = subscripted.getSubscriptionCost();
            subscriptionPayment(subscriberId, subscriptedId, cost * periods);

            subscription = new Subscription();
            subscription.setSubscriber(subscriber);
            subscription.setSubscripted(subscripted);
            subscription.setCost(cost);
            subscription.setExpirationTime(subscription
                    .getSubscriptionTime()
                    .plusWeeks((long) subscriptionProps.getSubscriptionDuration() * periods)
            );
            publisher.publishEvent(new Subscribed(subscriberId, subscriptedId));
        }
    }

    public void unsubscribe(String subscriberId, String subscriptedId) {
        var subscriber = userService.getReferenceById(subscriberId);
        var subscripted = userService.getReferenceById(subscriptedId);
        var subscription = subscriptionRepo.getReferenceById(new SubscriptionId(subscriber, subscripted));
        subscription.setExpirationTime(ZonedDateTime.now());
        publisher.publishEvent(new Unsubscribed(subscriberId, subscriptedId));
    }

    public boolean isSubscribedTo(String subscriberId, String subscriptedId) {
        var subscriber = userService.getReferenceById(subscriberId);
        var subscripted = userService.getReferenceById(subscriptedId);
        return subscriptionRepo.existsById(new SubscriptionId(subscriber, subscripted));
    }

    private void subscriptionPayment(String subscriberId, String subscriptedId, Double cost) {
        userService.updateBalance(subscriberId, -cost);
        userService.updateBalance(subscriptedId, cost * (1 - subscriptionProps.getSubscriptionFee()));
    }
}
