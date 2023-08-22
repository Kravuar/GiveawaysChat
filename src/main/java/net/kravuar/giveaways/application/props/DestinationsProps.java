package net.kravuar.giveaways.application.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.websocket.destinations")
@Data
public class DestinationsProps {
    /*
    * Destination value for notification messages.
    * */
    private String notifications;
    /*
     * Destination value for giveaway messages.
     * */
    private String giveawayAdded;
    /*
     * Destination value for giveaway collection messages.
     * */
    private String giveawayCollected;
}
