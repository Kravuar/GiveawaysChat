package net.kravuar.giveaways.domain.model;

import java.security.Principal;

public interface PrincipalWithId extends Principal {
//    TODO: Create authentication, which will use this principal
    String getId();
}
