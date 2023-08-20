package net.kravuar.giveaways.domain.messages;

import lombok.Data;

@Data
public class UserSuccessfullySubscribedMessage {
    private final String subscriptionUsername;
}
