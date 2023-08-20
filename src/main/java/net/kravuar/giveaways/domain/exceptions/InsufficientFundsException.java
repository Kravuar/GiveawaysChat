package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends RuntimeException{
    private final Long required;
    private final Long available;
    public InsufficientFundsException(Long required, Long available) {
        super("resource.not-found");
        this.required = required;
        this.available = available;
    }
}
