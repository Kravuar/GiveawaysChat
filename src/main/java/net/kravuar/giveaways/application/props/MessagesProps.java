package net.kravuar.giveaways.application.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.messages")
@Data
public class MessagesProps {
    /*
    * Message source for a giveaway exhaustion error message.
    * */
    private final String giveawayExhausted = "app.giveaway.collection.exhausted";
    /*
     * Message source for an inaccessible, private giveaway error message.
    * */
    private final String giveawayIsPrivate = "app.giveaway.collection.is-private";
    /*
     * Message source for an insufficient funds error message.
     * */
    private final String insufficientFunds = "user.balance.insufficient-funds";
    /*
     * Message source for a subscription absence error message.
     * */
    private final String subscriptionNotFound = "user.subscription.not-found";
}
