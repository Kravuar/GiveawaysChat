package net.kravuar.giveaways.application.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.services.subscription")
@Data
public class SubscriptionProps {
    /*
    * Duration in weeks.
    * */
    private Integer subscriptionDuration = 4;
}
