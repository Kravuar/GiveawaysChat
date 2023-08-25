package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final String name;
    private final String value;
}
