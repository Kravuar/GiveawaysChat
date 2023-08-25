package net.kravuar.giveaways.domain.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Subscribed {
    private final String subscriberId;
    private final String subscriptedId;
}
