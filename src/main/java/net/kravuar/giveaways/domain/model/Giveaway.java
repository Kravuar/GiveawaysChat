package net.kravuar.giveaways.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import net.kravuar.giveaways.domain.dto.GiveawayFormDTO;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;

@Table(name = "giveaways")
@Data
public class Giveaway {
    @Id
    private String id;

    @ManyToOne(optional = false)
    private User owner;

//    TODO: Set of users that collected this giveaway

    @Column(nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();
    @Column(nullable = false)
    private ZonedDateTime expiresAt;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Long amount;
    @Column(nullable = false)
    private Long count;
    @Column(nullable = false)
    private Boolean isPrivate;

    public Giveaway(User user, GiveawayFormDTO giveawayDTO) {
        this.owner = user;
        this.amount = giveawayDTO.getAmount();
        this.count = giveawayDTO.getCount();
        this.expiresAt = giveawayDTO.getExpiresAt();
        this.title = giveawayDTO.getTitle();
        this.isPrivate = giveawayDTO.getIsPrivate();
    }
}
