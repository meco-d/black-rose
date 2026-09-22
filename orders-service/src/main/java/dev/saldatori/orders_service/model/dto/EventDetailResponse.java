package dev.saldatori.orders_service.model.dto;

import dev.saldatori.orders_service.model.entity.Event;

import java.time.OffsetDateTime;

public record EventDetailResponse(
        Long id,
        String name,
        OffsetDateTime startsAt,
        Integer capacity,
        int ticketsRemaining
) {
    public static EventDetailResponse from(Event event, int ticketsRemaining) {
        return new EventDetailResponse(
                event.getId(), event.getName(), event.getStartsAt(), event.getCapacity(), ticketsRemaining);
    }
}
