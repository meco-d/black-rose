package dev.saldatori.orders_service.client;

import java.time.OffsetDateTime;

public record RemoteEvent(
        Long id,
        String name,
        OffsetDateTime startsAt,
        Integer capacity
) {
}
