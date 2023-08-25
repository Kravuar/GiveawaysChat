package net.kravuar.giveaways.domain.events;

import lombok.Data;

@Data
public class GiveawayCollected {
    private final String userId;
    private final String giveawayId;
}
