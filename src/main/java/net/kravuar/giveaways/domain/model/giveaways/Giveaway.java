package net.kravuar.giveaways.domain.model.giveaways;

import jakarta.persistence.*;
import lombok.Data;
import net.kravuar.giveaways.domain.dto.GiveawayFormDTO;
import net.kravuar.giveaways.domain.model.user.User;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "giveaways")
@Data
public class Giveaway {
    @Id
    private String id;

    @ManyToOne(optional = false)
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> collected = new HashSet<>();

    @Column(nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();
    @Column(nullable = false)
    private ZonedDateTime expiresAt;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private Long usages;
    @Column(nullable = false)
    private Boolean isPrivate;

//    Denormalized
    @Column(nullable = false)
    private Long usagesLeft;

    public Giveaway(User user, GiveawayFormDTO giveawayDTO) {
        this.owner = user;
        this.amount = giveawayDTO.getAmount();
        this.usages = giveawayDTO.getUsages();
        this.usagesLeft = giveawayDTO.getUsages();
        this.expiresAt = giveawayDTO.getExpiresAt();
        this.title = giveawayDTO.getTitle();
        this.isPrivate = giveawayDTO.getIsPrivate();
    }
}
