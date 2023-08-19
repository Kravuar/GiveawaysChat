package net.kravuar.fluxsocket.domain.messages;

import lombok.Data;

@Data
public class UserSuccessfullyConsumedGiveawayMessage {
    private final String giveawayId;
}
