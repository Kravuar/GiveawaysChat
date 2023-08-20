package net.kravuar.giveaways.domain.messages;

import lombok.Data;

@Data
public class UserFailedToSubscribeMessage {
    private final String subscriptedId;
    private final String message;
}
