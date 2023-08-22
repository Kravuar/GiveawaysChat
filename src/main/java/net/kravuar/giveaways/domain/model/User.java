package net.kravuar.giveaways.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"giveaways", "subscribers", "subscriptions"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    private Long balance = 0L;
    private Long subscriptionCost = null;

//    TODO: Denormalize to contain count for: giveaways, subscribers, subscribed

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Giveaway> giveaways = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany(mappedBy = "subscribers")
    private Set<User> subscriptions = new HashSet<>();
}
