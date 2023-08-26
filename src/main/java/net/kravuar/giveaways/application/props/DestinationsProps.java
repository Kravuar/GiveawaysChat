package net.kravuar.giveaways.application.props;

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
    private String giveaway;
    /*
     * Destination value for giveaway collection messages.
     * */
    private String giveawayCollected;
    /*
     * Destination value for balance messages.
     * */
    private String balanceUpdated;
    /*
     * Destination value for subscribed messages.
     * */
    private String subscribed;
    /*
     * Destination value for unsubscribed messages.
     * */
    private String unsubscribed;
}
