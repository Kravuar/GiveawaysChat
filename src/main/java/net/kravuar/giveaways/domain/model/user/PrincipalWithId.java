package net.kravuar.giveaways.domain.model.user;

import lombok.Data;

import java.security.Principal;

@Data
public class PrincipalWithId implements Principal {
    private final String id;
    private final String username;

    @Override
    public String getName() {
        return username;
    }
}
