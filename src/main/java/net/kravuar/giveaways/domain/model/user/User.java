package net.kravuar.giveaways.domain.model.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.kravuar.giveaways.domain.model.giveaways.Giveaway;
import net.kravuar.giveaways.domain.model.user.subscription.Subscription;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"giveaways", "subscriptions"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    private Double balance = 0D;
    private Double subscriptionCost = null;

//    TODO: Denormalize to contain count for: giveaways, subscribers, subscribed
//    TODO: add the above to the UserDTO

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Giveaway> giveaways = new HashSet<>();

    @OneToMany(mappedBy = "subscriber", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Subscription> subscriptions = new HashSet<>();
}
