package net.kravuar.giveaways.domain.security.messageInterceptors;

import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.services.SubscriptionService;
import net.kravuar.giveaways.domain.exceptions.SubscriptionRequiredException;
import net.kravuar.giveaways.domain.model.PrincipalWithId;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;

public class SubscriptionInterceptor implements ChannelInterceptor {
    private final String pattern;
    private final SubscriptionService subscriptionService;

    public SubscriptionInterceptor(DestinationsProps destinationsProps, SubscriptionService subscriptionService) {
        this.pattern = "/user/*/" + destinationsProps.getGiveawayAdded();
        this.subscriptionService = subscriptionService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getCommand() == StompCommand.SUBSCRIBE) {
            String destination = accessor.getDestination();
            if (destination.matches(pattern)) {
                var subscriberId = getCurrentUser().getId();
                var subscriptedId = destination.split("[/.]")[1];
                if (!subscriptionService.isSubscribedTo(subscriberId, subscriptedId))
                    throw new SubscriptionRequiredException();
            }
        }
        return message;
    }

    private PrincipalWithId getCurrentUser() {
        return (PrincipalWithId) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
