package net.kravuar.giveaways.domain.messages;

import lombok.Data;
import net.kravuar.giveaways.domain.model.Giveaway;

import java.time.ZonedDateTime;

@Data
public class GiveawayMessage {
    private final String id;
    private final String owner;
    private final Long amount;
    private final Long count;
    private final Boolean isPrivate;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime expiresAt;
    private final String title;

    public GiveawayMessage(Giveaway giveaway) {
        this.id = giveaway.getId();
        this.owner = giveaway.getOwner().getUsername();
        this.amount = giveaway.getAmount();
        this.count = giveaway.getUsages();
        this.createdAt = giveaway.getCreatedAt();
        this.expiresAt = giveaway.getExpiresAt();
        this.title = giveaway.getTitle();
        this.isPrivate = giveaway.getIsPrivate();
    }
}
