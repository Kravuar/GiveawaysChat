package net.kravuar.giveaways.domain.model.user.subscription;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kravuar.giveaways.domain.model.user.User;

import java.time.ZonedDateTime;

@Data
@Table(name = "subscriptions")
@NoArgsConstructor
@IdClass(SubscriptionId.class)
public class Subscription {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User subscriber;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User subscripted;

    @Column(nullable = false)
    private ZonedDateTime subscriptionTime = ZonedDateTime.now();
    @Column(nullable = false)
    private ZonedDateTime expirationTime;

    @Column(nullable = false)
    private Double cost;
}
