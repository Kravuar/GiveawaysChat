package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.lang.Nullable;

import java.util.Map;

@Getter
public abstract class ResolvableRuntimeException extends RuntimeException implements MessageSourceResolvable {
    private final String code;

    protected ResolvableRuntimeException(String code) {
        super(code);
        this.code = code;
    }

    @Nullable
    @Override
    public String[] getCodes() {
        return new String[]{code};
    }

    @Override
    public abstract Object[] getArguments();
}
