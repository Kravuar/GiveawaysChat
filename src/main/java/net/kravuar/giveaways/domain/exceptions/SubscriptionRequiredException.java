package net.kravuar.giveaways.domain.exceptions;

public class SubscriptionRequiredException extends RuntimeException{
    public SubscriptionRequiredException() {
        super("giveaway.subscription.required");
    }
}
