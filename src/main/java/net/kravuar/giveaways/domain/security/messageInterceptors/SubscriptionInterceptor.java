package net.kravuar.giveaways.domain.security.messageInterceptors;

import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.services.UserService;
import net.kravuar.giveaways.domain.exceptions.SubscriptionRequiredException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SubscriptionInterceptor implements ChannelInterceptor {
    private final String pattern;
    private final UserService userService;

    public SubscriptionInterceptor(DestinationsProps destinationsProps, UserService userService) {
        this.pattern = "/user/*/" + destinationsProps.giveawayAdded;
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getCommand() == StompCommand.SUBSCRIBE) {
            String destination = accessor.getDestination();
            if (destination.matches(pattern)) {
                var subscriberUsername = getCurrentUser().getName();
                var subscriptedUsername = destination.split("[/.]")[1];
                if (!userService.isSubscribedTo(subscriberUsername, subscriptedUsername))
                    throw new SubscriptionRequiredException();
            }
        }
        return message;
    }

    private Authentication getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
