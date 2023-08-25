package net.kravuar.giveaways.application.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.DestinationsProps;
import net.kravuar.giveaways.domain.events.BalanceUpdated;
import net.kravuar.giveaways.domain.events.GiveawayPublished;
import net.kravuar.giveaways.domain.events.GiveawayCollected;
import net.kravuar.giveaways.domain.messages.BalanceUpdatedMessage;
import net.kravuar.giveaways.domain.messages.GiveawayCounterDecreaseMessage;
import net.kravuar.giveaways.domain.messages.GiveawayMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MessageService messageService;
    private final DestinationsProps destinationsProps;

//    TODO: Maybe send notifications along with some of those

    @EventListener(condition = "#event.giveaway.isPrivate")
    public void handlePrivateGiveaway(GiveawayPublished event) {
        messageService.sendToUser(
                event.getGiveaway().getOwner().getId(),
                destinationsProps.getGiveaway(),
                new GiveawayMessage(event.getGiveaway())
        );
    }

    @EventListener(condition = "!#event.giveaway.isPrivate")
    public void handlePublicGiveaway(GiveawayPublished event) {
        messageService.send(
                destinationsProps.getGiveaway(),
                new GiveawayMessage(event.getGiveaway())
        );
    }

    @EventListener
    public void handleGiveawayCollection(GiveawayCollected event) {
        messageService.send(
                destinationsProps.getGiveawayCollected(),
                new GiveawayCounterDecreaseMessage(event.getGiveawayId())
        );
    }

    @EventListener
    public void handleBalanceUpdate(BalanceUpdated event) {
        messageService.sendToUser(
                event.getUserId(),
                destinationsProps.getBalanceUpdated(),
                new BalanceUpdatedMessage(event.getDelta(), event.getNewBalance())
        );
    }
}
