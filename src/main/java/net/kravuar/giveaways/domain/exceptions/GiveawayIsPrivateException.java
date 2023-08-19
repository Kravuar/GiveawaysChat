package net.kravuar.giveaways.domain.exceptions;

public class GiveawayIsPrivateException extends UserException {
    public GiveawayIsPrivateException(String username) {
        super("giveaway.consumption.is-private", username);
    }
}
