package net.kravuar.giveaways.application.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.services.MessageService;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class Advice {
    private final MessageService messageService;

//    TODO: MessageExceptions with @SendToUser(broadcast=false). All exceptions should be sent to current message Authentication user
}
