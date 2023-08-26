package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class GiveawayExhaustedException extends ResolvableRuntimeException {
    private final String giveawayId;

    public GiveawayExhaustedException(String giveawayId) {
        super("app.giveaway.collection.exhausted");
        this.giveawayId = giveawayId;
    }

    @Override
    public Object[] getArguments() {
        return new Object[] {giveawayId};
    }
}
