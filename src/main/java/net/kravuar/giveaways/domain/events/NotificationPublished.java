package net.kravuar.giveaways.domain.events;

import lombok.Data;
import net.kravuar.giveaways.domain.messages.NotificationType;

@Data
public class NotificationPublished {
    private final String userId;
    private final NotificationType type;
    private final String message;
    private final Object[] args;
}
