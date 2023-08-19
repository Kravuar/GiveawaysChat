package net.kravuar.giveaways.domain.messages;

import lombok.Data;

@Data
public class UserCreatedGiveawayMessage {

    private final Long totalCount;
    private final String title;
}
