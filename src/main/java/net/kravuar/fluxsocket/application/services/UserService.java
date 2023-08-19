package net.kravuar.fluxsocket.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.fluxsocket.application.repo.UserRepository;
import net.kravuar.fluxsocket.domain.exceptions.InsufficientFundsException;
import net.kravuar.fluxsocket.domain.model.User;
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
