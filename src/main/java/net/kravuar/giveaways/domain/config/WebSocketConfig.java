package net.kravuar.giveaways.domain.config;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.application.props.WebSocketProps;
import net.kravuar.giveaways.application.services.SubscriptionService;
import net.kravuar.giveaways.domain.security.messageInterceptors.SubscriptionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketProps webSocketProps;
    private final DestinationsProps destinationsProps;
    SubscriptionService subscriptionService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/giveaways-ws");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/giveaways-connect")
                .setAllowedOrigins(webSocketProps.getAllowedOrigins().toArray(new String[0]))
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
//        TODO: addInterceptors for authentication and locale propagation (from handshake http)
//        TODO: addInterceptors for subscribe validation upon db
        registration.interceptors(new SubscriptionInterceptor(destinationsProps, subscriptionService));
    }

//    This thing should be able to resolve locale from WebSocketSession
    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }
}