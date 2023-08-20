package net.kravuar.giveaways.application.props;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.websocket.destinations")
@AllArgsConstructor
public class DestinationsProps {
    public String notifications;
    public String giveawayAdded;
    public String giveawayConsumed;
}
