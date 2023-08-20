package net.kravuar.giveaways.domain.config;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.WebSocketProps;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketProps webSocketProps;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/giveaways-app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        TODO: addInterceptors for authentication
//        TODO: addInterceptors for subscribe validation upon db
        registry.addEndpoint("/giveaways-connect")
                .setAllowedOrigins(webSocketProps.allowedOrigins.toArray(new String[0]))
                .withSockJS();
    }
}