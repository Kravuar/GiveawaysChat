package net.kravuar.giveaways.domain.messages;

import lombok.Data;

@Data
public class BalanceUpdatedMessage {
    private final Double delta;
    private final Double newBalance;
}
