package net.kravuar.giveaways.application.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.domain.exceptions.ResolvableRuntimeException;
import net.kravuar.giveaways.domain.messages.ErrorMessage;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class Advice {
    private final MessageSource messageSource;

//    TODO: I'd prefer the destination to be acquired from props

//    TODO: Probably locale resolver does nothing

    @MessageExceptionHandler
    @SendToUser(value = "/topic/error", broadcast = false)
    public ErrorMessage handleCustom(ResolvableRuntimeException exception) {
        return new ErrorMessage(exception, messageSource, LocaleContextHolder.getLocale());

    }

    @MessageExceptionHandler
    @SendToUser(value = "/topic/error", broadcast = false)
    public ErrorMessage handleSpring(MessageSourceResolvable exception) {
        return new ErrorMessage(exception.getCodes()[0], ((Exception) exception).getLocalizedMessage());
    }
}
