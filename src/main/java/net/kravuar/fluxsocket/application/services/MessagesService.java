package net.kravuar.fluxsocket.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.fluxsocket.domain.messages.GiveawayCounterDecreaseMessage;
import net.kravuar.fluxsocket.domain.messages.GiveawayMessage;
import net.kravuar.fluxsocket.domain.messages.UserFailedToConsumeGiveawayMessage;
import net.kravuar.fluxsocket.domain.messages.UserSuccessfullyConsumedGiveawayMessage;
import net.kravuar.fluxsocket.domain.model.Giveaway;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagesService {
    private final SimpMessagingTemplate template;

    public void sendNewPrivateGiveaway(Giveaway giveaway, String destination) {
        template.convertAndSendToUser(
                giveaway.getOwner().getUsername(),
                destination,
                new GiveawayMessage(giveaway)
        );
    }

    public void sendNewPublicGiveaway(Giveaway giveaway, String destination) {
        template.convertAndSend(
                destination,
                new GiveawayMessage(giveaway)
        );
    }

    public void sendUserSuccessfullyConsumedGiveaway(String username, String giveawayId, String destination) {
        template.convertAndSendToUser(
                username,
                destination,
                new UserSuccessfullyConsumedGiveawayMessage(giveawayId)
        );
    }

    public void sendUserFailedToConsumeGiveaway(String username, String giveawayId, String destination, String message) {
        template.convertAndSendToUser(
                username,
                destination,
                new UserFailedToConsumeGiveawayMessage(giveawayId, message)
        );
    }

    public void sendCounterDecrease(String giveawayId, String destination) {
        template.convertAndSend(
                destination,
                new GiveawayCounterDecreaseMessage(giveawayId)
        );
    }
}
