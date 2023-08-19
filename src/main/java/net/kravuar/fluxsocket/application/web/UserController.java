package net.kravuar.fluxsocket.application.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.fluxsocket.application.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    TODO: SubscribeMapping for payed subscriptions
}
