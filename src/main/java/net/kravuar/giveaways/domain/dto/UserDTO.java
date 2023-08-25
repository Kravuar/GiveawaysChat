package net.kravuar.giveaways.domain.dto;

import lombok.Data;
import net.kravuar.giveaways.domain.model.user.User;

@Data
public class UserDTO {
    private final String id;
    private final String username;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
