package net.kravuar.giveaways.domain.messages;

import lombok.Data;

@Data
public class UserFailedToConsumeGiveawayMessage {
    private final String giveawayId;
    private final String message;
}
