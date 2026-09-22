package dev.saldatori.orders_service.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record EventRequest(
        @NotBlank String name,
        @NotNull @Future OffsetDateTime startsAt,
        @NotNull @Positive Integer capacity
) {
}
