package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.domain.messages.GiveawayCounterDecreaseMessage;
import net.kravuar.giveaways.domain.messages.GiveawayMessage;
import net.kravuar.giveaways.domain.messages.UserFailedToConsumeGiveawayMessage;
import net.kravuar.giveaways.domain.messages.UserSuccessfullyConsumedGiveawayMessage;
import net.kravuar.giveaways.domain.model.Giveaway;
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
