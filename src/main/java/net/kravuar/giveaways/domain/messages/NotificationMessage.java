package net.kravuar.giveaways.domain.messages;

import lombok.Data;

@Data
public class NotificationMessage {
    private final NotificationType type;
    private final String message;
}
