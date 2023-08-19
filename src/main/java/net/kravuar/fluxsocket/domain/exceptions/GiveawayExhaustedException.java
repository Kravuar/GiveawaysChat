package net.kravuar.fluxsocket.domain.exceptions;

public class GiveawayExhaustedException extends UserException{
    public GiveawayExhaustedException(String username) {
        super("giveaway.consumption.exhausted", username);
    }
}
