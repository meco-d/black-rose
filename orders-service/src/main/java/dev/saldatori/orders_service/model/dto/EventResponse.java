package dev.saldatori.orders_service.model.dto;

import dev.saldatori.orders_service.model.entity.Event;

import java.time.OffsetDateTime;

public record EventResponse(
        Long id,
        String name,
        OffsetDateTime startsAt,
        Integer capacity
) {
    public static EventResponse from(Event event) {
        return new EventResponse(event.getId(), event.getName(), event.getStartsAt(), event.getCapacity());
    }
}
