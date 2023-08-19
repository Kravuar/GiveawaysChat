package net.kravuar.fluxsocket.domain.exceptions;

public class GiveawayIsPrivateException extends UserException {
    public GiveawayIsPrivateException(String username) {
        super("giveaway.consumption.is-private", username);
    }
}
