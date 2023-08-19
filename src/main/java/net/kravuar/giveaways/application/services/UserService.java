package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.repo.UserRepository;
import net.kravuar.giveaways.domain.exceptions.InsufficientFundsException;
import net.kravuar.giveaways.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updateBalance(User user, Long delta) {
        var balance = user.getBalance();
        if (balance + delta <= 0)
            throw new InsufficientFundsException(user.getUsername(), delta, balance);
        userRepository.save(user);
    }
}
