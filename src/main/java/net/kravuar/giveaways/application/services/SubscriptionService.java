package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.props.SubscriptionProps;
import net.kravuar.giveaways.application.repo.SubscriptionRepo;
import net.kravuar.giveaways.domain.exceptions.InsufficientFundsException;
import net.kravuar.giveaways.domain.messages.UserSuccessfullySubscribedMessage;
import net.kravuar.giveaways.domain.model.Subscription;
import net.kravuar.giveaways.domain.model.SubscriptionId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepo subscriptionRepo;
    private final UserService userService;
    private final MessageService messageService;
    private final SubscriptionProps subscriptionProps;
    private final DestinationsProps destinationsProps;

    @Scheduled(cron = "0 0 */3 * * *")
    public void renewSubscriptions() {
        var now = ZonedDateTime.now();

        for (var subscription : subscriptionRepo.findAll())
            if (subscription.getExpirationTime().isBefore(ChronoZonedDateTime.from(now))) {
                var subscriberId = subscription.getSubscriber().getId();
                var subscriptedId = subscription.getSubscripted().getId();
                try {
                    userService.transfer(subscriberId, subscriptedId, subscription.getCost());
                } catch (InsufficientFundsException exception) {
                    unsubscribe(subscriberId, subscriptedId);
//                    TODO: notify
                }
            }
//        TODO: notify
    }

    public void subscribe(String subscriberId, String subscriptedId, Integer periods) {
        var subscriber = userService.findByIdOrElseThrow(subscriberId);
        var subscripted = userService.findByIdOrElseThrow(subscriptedId);

        var cost = subscripted.getSubscriptionCost();
        userService.transfer(subscriberId, subscriptedId, cost * periods);

        var subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setSubscripted(subscripted);
        subscription.setCost(cost);
        subscription.setExpirationTime(subscription
                .getSubscriptionTime()
                .plusWeeks(subscriptionProps.getSubscriptionDuration())
        );
        subscriptionRepo.save(subscription);

//        TODO: notify both users
        messageService.sendToUser(
                subscriberId,
                destinationsProps.getNotifications(),
                new UserSuccessfullySubscribedMessage(subscriptedId)
        );
    }

    public void unsubscribe(String subscriberId, String subscriptedId) {
        var subscriber = userService.getReferenceById(subscriberId);
        var subscripted = userService.getReferenceById(subscriptedId);
        subscriptionRepo.deleteById(new SubscriptionId(subscriber, subscripted));
    }

    public boolean isSubscribedTo(String subscriberId, String subscriptedId) {
        var subscriber = userService.getReferenceById(subscriberId);
        var subscripted = userService.getReferenceById(subscriptedId);
        return subscriptionRepo.existsById(new SubscriptionId(subscriber, subscripted));
    }
}
