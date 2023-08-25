package net.kravuar.giveaways.domain.events;

import lombok.Data;

@Data
public class BalanceUpdated {
    private final String userId;
    private final Double delta;
    private final Double newBalance;
}
