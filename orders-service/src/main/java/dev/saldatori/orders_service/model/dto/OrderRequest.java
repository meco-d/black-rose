package dev.saldatori.orders_service.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
        @NotNull Long eventId,
        @NotBlank @Email String customerEmail,
        @NotNull @Positive Integer quantity
) {
}
