package net.kravuar.fluxsocket.application.web;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class Advice {
    private final SimpMessagingTemplate template;
//    TODO: MessageExceptions with @SendToUser(broadcast=false)
}
