package net.kravuar.giveaways.domain.messages;

import lombok.Data;

@Data
public class UserSuccessfullyConsumedGiveawayMessage {
    private final String giveawayId;
}
