package net.kravuar.giveaways.domain.exceptions;

public class GiveawayIsPrivateException extends RuntimeException{
    public GiveawayIsPrivateException() {
        super("giveaway.consumption.is-private");
    }
}
