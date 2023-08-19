package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends UserException {
    private final String name;
    private final String value;
    public ResourceNotFoundException(String username, String name, String value) {
        super("resource.not-found", username);
        this.name = name;
        this.value = value;
    }
}
