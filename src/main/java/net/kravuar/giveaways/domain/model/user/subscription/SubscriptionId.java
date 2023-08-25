package net.kravuar.giveaways.domain.model.user.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.kravuar.giveaways.domain.model.user.User;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SubscriptionId implements Serializable {
    private User subscriber;
    private User subscripted;
}
