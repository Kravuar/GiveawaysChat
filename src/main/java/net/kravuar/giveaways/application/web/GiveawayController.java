package net.kravuar.giveaways.application.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.ControllersProps;
import net.kravuar.giveaways.application.services.GiveawayService;
import net.kravuar.giveaways.domain.dto.NewGiveawayDTO;
import net.kravuar.giveaways.domain.messages.GiveawayMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GiveawayController {
    private final GiveawayService giveawayService;
    private final ControllersProps controllersProps;

    @GetMapping(value = {"/giveaways/{page}/{pageSize}", "/giveaways/{page}/"})
    public Collection<GiveawayMessage> findVisibleByPage(Principal principal, @PathVariable Integer page, @PathVariable Optional<Integer> pageSize) {
        return giveawayService.findVisible(
                principal.getName(),
                PageRequest.of(page, pageSize.orElse(controllersProps.pageSize))
        ).stream()
                .map(GiveawayMessage::new)
                .toList();
    }

    @SubscribeMapping("/giveaways")
    public Collection<GiveawayMessage> retrieveGiveawaysHistory(Principal principal) {
        return findVisibleByPage(principal, 0, Optional.empty());
    }

    @MessageMapping("/giveaways")
    public void addGiveaway(@Valid NewGiveawayDTO newGiveawayDTO, Principal principal) {
        giveawayService.tryAddByUser(newGiveawayDTO, principal.getName());
    }

    @MessageMapping("/giveaways/{id}/apply")
    public void useGiveaway(@DestinationVariable String id, Principal principal) {
        giveawayService.tryConsumeByUser(id, principal.getName());
    }
}
