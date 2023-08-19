package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final String username;
    public UserException(String message, String username) {
        super(message);
        this.username = username;
    }
}
