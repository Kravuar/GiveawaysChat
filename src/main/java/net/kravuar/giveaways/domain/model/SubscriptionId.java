package net.kravuar.giveaways.domain.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubscriptionId implements Serializable {
    private User subscriber;
    private User subscripted;
}
