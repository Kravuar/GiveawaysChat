package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InsufficientFundsException extends RuntimeException{
    private final Double required;
    private final Double available;
}
