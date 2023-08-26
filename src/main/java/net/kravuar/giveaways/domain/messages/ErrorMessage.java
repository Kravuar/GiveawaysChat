package net.kravuar.giveaways.domain.messages;

import lombok.Getter;
import net.kravuar.giveaways.domain.exceptions.ResolvableRuntimeException;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;

import java.util.Locale;

@Getter
public class ErrorMessage {
    private final String code;
    private final String message;

    public ErrorMessage(ResolvableRuntimeException exception, MessageSource messageSource, @Nullable Locale locale) {
        this.code = exception.getCode();
        this.message = messageSource.getMessage(
                exception,
                locale == null
                        ? Locale.getDefault()
                        : locale
                );
    }
    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
