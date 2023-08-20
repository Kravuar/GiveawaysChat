package net.kravuar.giveaways.application.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.ControllersProps;
import net.kravuar.giveaways.application.services.GiveawayService;
import net.kravuar.giveaways.domain.dto.GiveawayFormDTO;
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
    public Collection<GiveawayMessage> findVisibleByPage(String username, @PathVariable Integer page, @PathVariable Optional<Integer> pageSize) {
        return giveawayService.findVisible(
                username,
                PageRequest.of(page, pageSize.orElse(controllersProps.pageSize))
        ).stream()
                .map(GiveawayMessage::new)
                .toList();
    }

    @SubscribeMapping("/giveaways")
    public Collection<GiveawayMessage> initHistory(Principal principal) {
        return findVisibleByPage(principal.getName(), 0, Optional.empty());
    }

    @MessageMapping("/giveaways")
    public void addGiveaway(Principal principal, @Valid GiveawayFormDTO giveawayFormDTO) {
        giveawayService.addByUser(giveawayFormDTO, principal.getName());
    }

    @MessageMapping("/giveaways/{id}/apply")
    public void useGiveaway(Principal principal, @DestinationVariable String id) {
        giveawayService.consumeByUser(id, principal.getName());
    }
}
