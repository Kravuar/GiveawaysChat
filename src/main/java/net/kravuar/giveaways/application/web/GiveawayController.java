package net.kravuar.giveaways.application.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.application.props.ControllersProps;
import net.kravuar.giveaways.application.services.GiveawayService;
import net.kravuar.giveaways.domain.dto.GiveawayFormDTO;
import net.kravuar.giveaways.domain.messages.GiveawayMessage;
import net.kravuar.giveaways.domain.model.user.PrincipalWithId;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GiveawayController {
    private final GiveawayService giveawayService;
    private final ControllersProps controllersProps;

    @GetMapping(value = {"/giveaways/{page}/{pageSize}", "/giveaways/{page}/"})
    public Collection<GiveawayMessage> findVisibleByPage(@AuthenticationPrincipal PrincipalWithId principal, @PathVariable Integer page, @PathVariable Optional<Integer> pageSize) {
        return giveawayService.findVisible(
                principal != null
                        ? principal.getId()
                        : null,
                PageRequest.of(page, pageSize.orElse(controllersProps.getPageSize()))
        ).stream()
                .map(GiveawayMessage::new)
                .toList();
    }

    @SubscribeMapping("/giveaways")
    public Collection<GiveawayMessage> initHistory(@AuthenticationPrincipal PrincipalWithId principal) {
        return findVisibleByPage(principal, 0, Optional.empty());
    }

    @MessageMapping("/giveaways")
    public void addGiveaway(@AuthenticationPrincipal PrincipalWithId principal, @Valid GiveawayFormDTO giveawayFormDTO) {
        giveawayService.addByUser(giveawayFormDTO, principal.getId());
    }

    @MessageMapping("/giveaways/{id}/apply")
    public void useGiveaway(@AuthenticationPrincipal PrincipalWithId principal, @DestinationVariable String id) {
        giveawayService.collectByUser(id, principal.getId());
    }
}
