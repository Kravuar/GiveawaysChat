package net.kravuar.giveaways.domain.events;

import lombok.Data;
import net.kravuar.giveaways.domain.model.giveaways.Giveaway;

@Data
public class GiveawayPublished {
    private final Giveaway giveaway;
}
