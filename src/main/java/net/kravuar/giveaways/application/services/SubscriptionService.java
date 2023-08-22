package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * *")
    public void expireSubscriptions() {
        var now = LocalDate.now();

        //    TODO: implement scheduled expiration
        for (var user: userService.findAll()) {
//            Find all expired subscription
//            foreach:
//            userService.unsubscribe(, );
        }
    }
}
