package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.repo.UserRepository;
import net.kravuar.giveaways.domain.exceptions.InsufficientFundsException;
import net.kravuar.giveaways.domain.exceptions.ResourceNotFoundException;
import net.kravuar.giveaways.domain.messages.UserSuccessfullySubscribedMessage;
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

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updateBalance(String username, Long delta) {
        var user = findByUsername(username);

        if (user == null)
            throw new ResourceNotFoundException("user", username);

        var balance = user.getBalance();
        var newBalance = balance + delta;
        if (newBalance <= 0)
            throw new InsufficientFundsException(delta, balance);
        user.setBalance(newBalance);
    }

    public void subscribe(String subscriberUsername, String subscriptedUsername) {
        var subscriber = findByUsername(subscriberUsername);
        var subscripted = findByUsername(subscriptedUsername);

        if (subscriber == null)
            throw new ResourceNotFoundException("user", subscriberUsername);

        if (subscripted == null)
            throw new ResourceNotFoundException("user", subscriptedUsername);

        var cost = subscripted.getSubscriptionCost();
        var available = subscriber.getBalance();
        if (cost > available)
            throw new InsufficientFundsException(cost, available);

        updateBalance(subscriberUsername, -cost);
        updateBalance(subscriptedUsername, cost);

        subscriber.getSubscriptions().add(subscripted);

        messageService.sendToUser(
                subscriberUsername,
                destinationsProps.notifications,
                new UserSuccessfullySubscribedMessage(subscriptedUsername)
        );
    }
}
