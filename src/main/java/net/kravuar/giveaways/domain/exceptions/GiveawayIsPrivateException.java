package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class GiveawayIsPrivateException extends ResolvableRuntimeException {
    private final String giveawayId;

    public GiveawayIsPrivateException(String giveawayId) {
        super("app.giveaway.collection.is-private");
        this.giveawayId = giveawayId;
    }

    @Override
    public Object[] getArguments() {
        return new Object[] {giveawayId};
    }
}
