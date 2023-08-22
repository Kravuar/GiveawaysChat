package net.kravuar.giveaways.application.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.services.SubscriptionService;
import net.kravuar.giveaways.domain.model.PrincipalWithId;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @MessageMapping("/subscription/subscribe/{userId}/{periods}")
    public void subscribe(@AuthenticationPrincipal PrincipalWithId principal, @DestinationVariable String userId, Integer periods) {
        subscriptionService.subscribe(principal.getId(), userId, periods);
    }

    @MessageMapping("/subscription/unsubscribe/{userId}")
    public void unsubscribe(@AuthenticationPrincipal PrincipalWithId principal, @DestinationVariable String userId) {
        subscriptionService.unsubscribe(principal.getId(), userId);
    }
}
