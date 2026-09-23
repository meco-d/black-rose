package dev.saldatori.orders_service.model.dto;

import dev.saldatori.orders_service.client.RemoteEvent;

import java.time.OffsetDateTime;

public record EventAvailabilityResponse(
        Long id,
        String name,
        OffsetDateTime startsAt,
        Integer capacity,
        int ticketsRemaining
) {
    public static EventAvailabilityResponse from(RemoteEvent event, int ticketsRemaining) {
        return new EventAvailabilityResponse(
                event.id(), event.name(), event.startsAt(), event.capacity(), ticketsRemaining);
    }
}
