package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.repo.UserRepository;
import net.kravuar.giveaways.domain.events.BalanceUpdated;
import net.kravuar.giveaways.domain.exceptions.InsufficientFundsException;
import net.kravuar.giveaways.domain.exceptions.ResourceNotFoundException;
import net.kravuar.giveaways.domain.model.user.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;

    public User getReferenceById(String userId) { return userRepository.getReferenceById(userId); }

    public User findByIdOrElseThrow(String userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new ResourceNotFoundException("user", userId);
        return user.get();
    }

    public Double updateBalance(String userId, Double delta) {
        var user = findByIdOrElseThrow(userId);

        var balance = user.getBalance();
        var newBalance = balance + delta;
        if (newBalance <= 0)
            throw new InsufficientFundsException(delta, balance);
        user.setBalance(newBalance);

        publisher.publishEvent(new BalanceUpdated(userId, delta, newBalance));
        return newBalance;
    }
}
