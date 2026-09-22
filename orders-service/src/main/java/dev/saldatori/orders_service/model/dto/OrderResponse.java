package dev.saldatori.orders_service.model.dto;

import dev.saldatori.orders_service.model.entity.Order;
import dev.saldatori.orders_service.model.entity.Status;

import java.time.OffsetDateTime;

public record OrderResponse(
        Long id,
        Long eventId,
        String customerEmail,
        Integer quantity,
        Status status,
        OffsetDateTime createdAt
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(), order.getEventId(), order.getCustomerEmail(),
                order.getQuantity(), order.getStatus(), order.getCreatedAt());
    }
}
