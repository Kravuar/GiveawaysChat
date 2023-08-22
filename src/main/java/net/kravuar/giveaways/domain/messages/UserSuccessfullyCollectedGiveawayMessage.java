package net.kravuar.giveaways.domain.messages;

import lombok.Data;

@Data
public class UserSuccessfullyCollectedGiveawayMessage {
    private final String giveawayId;
}
