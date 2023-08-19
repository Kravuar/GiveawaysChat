package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends UserException {
    private final Long required;
    private final Long available;
    public InsufficientFundsException(String username, Long required, Long available) {
        super("resource.not-found", username);
        this.required = required;
        this.available = available;
    }
}
