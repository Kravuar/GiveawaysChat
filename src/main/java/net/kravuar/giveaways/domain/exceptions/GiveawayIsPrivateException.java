package net.kravuar.giveaways.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GiveawayIsPrivateException extends RuntimeException {
    private final String giveawayId;
}
