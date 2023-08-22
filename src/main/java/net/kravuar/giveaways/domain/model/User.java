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
@EqualsAndHashCode(exclude = {"giveaways", "subscriptions"})
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
//    TODO: add the above to the UserDTO

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Giveaway> giveaways = new HashSet<>();

    @OneToMany(mappedBy = "subscriber", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Subscription> subscriptions = new HashSet<>();
}
