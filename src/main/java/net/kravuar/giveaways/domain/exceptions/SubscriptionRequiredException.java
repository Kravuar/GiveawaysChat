package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class SubscriptionRequiredException extends ResolvableRuntimeException {
    private final String targetUserId;

    public SubscriptionRequiredException(String targetUserId) {
        super("app.resource.not-found");
        this.targetUserId = targetUserId;
    }

    @Override
    public Object[] getArguments() {
        return new Object[] {targetUserId};
    }
}
