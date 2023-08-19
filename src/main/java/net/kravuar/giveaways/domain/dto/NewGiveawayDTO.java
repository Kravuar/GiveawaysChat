package net.kravuar.giveaways.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class NewGiveawayDTO {
    @Min(1)
    private final Long amount;
    @Min(1)
    private final Long count;
    @NotNull
    private final Boolean isPrivate;
    @NotNull
    private final ZonedDateTime expiresAt;
    @NotBlank
    private final String title;
}
