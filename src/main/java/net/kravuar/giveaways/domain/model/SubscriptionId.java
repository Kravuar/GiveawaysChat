package net.kravuar.giveaways.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SubscriptionId implements Serializable {
    private User subscriber;
    private User subscripted;
}
