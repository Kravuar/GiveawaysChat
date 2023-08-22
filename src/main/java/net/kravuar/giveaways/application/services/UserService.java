package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.repo.UserRepository;
import net.kravuar.giveaways.domain.exceptions.InsufficientFundsException;
import net.kravuar.giveaways.domain.exceptions.ResourceNotFoundException;
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

    public User getReferenceById(String userId) { return userRepository.getReferenceById(userId); }

    public Iterable<User> findAll() { return userRepository.findAll(); }

    public User findByIdOrElseThrow(String userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new ResourceNotFoundException("user", userId);
        return user.get();
    }

    public void updateBalance(String userId, Long delta) {
        var user = findByIdOrElseThrow(userId);

        var balance = user.getBalance();
        var newBalance = balance + delta;
        if (newBalance <= 0)
            throw new InsufficientFundsException(delta, balance);
        user.setBalance(newBalance);
//        TODO: notify
    }

    public void transfer(String fromUserId, String toUserId, Long delta) {
        updateBalance(fromUserId, -delta);
        updateBalance(toUserId, delta);
    }
}
