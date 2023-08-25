package net.kravuar.giveaways.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class GiveawayFormDTO {
    @Min(1)
    private final Double amount;
    @Min(1)
    private final Long usages;
    @NotNull
    private final Boolean isPrivate;
    @NotNull
    private final ZonedDateTime expiresAt;
    @NotBlank
    private final String title;
}
