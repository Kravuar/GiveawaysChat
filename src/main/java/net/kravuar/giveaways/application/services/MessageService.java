package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final SimpMessagingTemplate template;

    public void send(String destination, Object payload) {
        template.convertAndSend(
                destination,
                payload
        );
    }

    public void sendToUser(String userId, String destination, Object payload) {
        template.convertAndSendToUser(
                userId,
                destination,
                payload
        );
    }
}
