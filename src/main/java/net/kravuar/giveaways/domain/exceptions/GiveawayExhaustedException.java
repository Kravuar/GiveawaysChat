package net.kravuar.giveaways.domain.exceptions;

public class GiveawayExhaustedException extends RuntimeException{
    public GiveawayExhaustedException() {
        super("giveaway.collection.exhausted");
    }
}
