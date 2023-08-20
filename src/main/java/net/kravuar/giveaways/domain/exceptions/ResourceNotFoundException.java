package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final String name;
    private final String value;
    public ResourceNotFoundException(String name, String value) {
        super("resource.not-found");
        this.name = name;
        this.value = value;
    }
}
