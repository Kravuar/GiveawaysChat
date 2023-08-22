package net.kravuar.giveaways.application.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.services.UserService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
