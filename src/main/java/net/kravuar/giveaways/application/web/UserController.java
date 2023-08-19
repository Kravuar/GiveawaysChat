package net.kravuar.giveaways.application.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    TODO: SubscribeMapping for payed subscriptions
}
