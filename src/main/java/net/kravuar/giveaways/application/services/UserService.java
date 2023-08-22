package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.repo.UserRepository;
import net.kravuar.giveaways.domain.exceptions.InsufficientFundsException;
import net.kravuar.giveaways.domain.exceptions.ResourceNotFoundException;
import net.kravuar.giveaways.domain.messages.UserSuccessfullySubscribedMessage;
import net.kravuar.giveaways.domain.model.Subscription;
import net.kravuar.giveaways.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final MessageService messageService;
    private final DestinationsProps destinationsProps;

//    TODO: need to remove this username binding and use id

    public Iterable<User> findAll() { return userRepository.findAll(); }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User findByUsernameOrElseThrow(String username) {
        var user = findByUsername(username);
        if (user == null)
            throw new ResourceNotFoundException("user", username);
        return user;
    }

    public void updateBalance(String username, Long delta) {
        var user = findByUsernameOrElseThrow(username);

        var balance = user.getBalance();
        var newBalance = balance + delta;
        if (newBalance <= 0)
            throw new InsufficientFundsException(delta, balance);
        user.setBalance(newBalance);
    }

    public void subscribe(String subscriberUsername, String subscriptedUsername) {
        var subscriber = findByUsernameOrElseThrow(subscriberUsername);
        var subscripted = findByUsernameOrElseThrow(subscriptedUsername);

        var cost = subscripted.getSubscriptionCost();
        var available = subscriber.getBalance();
        if (cost > available)
            throw new InsufficientFundsException(cost, available);

        updateBalance(subscriberUsername, -cost);
        updateBalance(subscriptedUsername, cost);

        var subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setSubscripted(subscripted);
//        TODO: subscription duration from props
//        TODO: allow pay for multiple periods at once
//        subscription.setExpirationTime();
        subscriber.getSubscriptions().add(subscription);

        messageService.sendToUser(
                subscriberUsername,
                destinationsProps.notifications,
                new UserSuccessfullySubscribedMessage(subscriptedUsername)
        );
    }

    public void unsubscribe(String subscriberUsername, String subscriptedUsername) {
//        TODO: implement unsubscribe
    }

    public boolean isSubscribedTo(String subscriberUsername, String subscriptedUsername) {
        var subscriber = findByUsernameOrElseThrow(subscriberUsername);
        var subscripted = findByUsernameOrElseThrow(subscriptedUsername);

//        TODO: fix excessive fetching
        return subscriber
                .getSubscriptions().stream()
                .anyMatch(subscription -> subscription.getSubscripted().equals(subscripted));
    }
}
