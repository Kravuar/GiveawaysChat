package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends ResolvableRuntimeException {
    private final String name;
    private final String value;

    public ResourceNotFoundException(String name, String value) {
        super("app.resource.not-found");
        this.name = name;
        this.value = value;
    }

    @Override
    public Object[] getArguments() {
        return new Object[] {name, value};
    }
}
