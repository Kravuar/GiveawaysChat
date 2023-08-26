package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends ResolvableRuntimeException {
    private final Double required;
    private final Double available;

    public InsufficientFundsException(Double required, Double available) {
        super("user.balance.insufficient-funds");
        this.required = required;
        this.available = available;
    }

    @Override
    public Object[] getArguments() {
        return new Object[] {required, available};
    }
}
