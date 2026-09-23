package dev.saldatori.orders_service.service;

import dev.saldatori.orders_service.client.EventClient;
import dev.saldatori.orders_service.client.RemoteEvent;
import dev.saldatori.orders_service.exception.InsufficientCapacityException;
import dev.saldatori.orders_service.exception.InvalidOrderStateException;
import dev.saldatori.orders_service.exception.OrderNotFoundException;
import dev.saldatori.orders_service.model.dto.OrderRequest;
import dev.saldatori.orders_service.model.entity.Order;
import dev.saldatori.orders_service.model.entity.Status;
import dev.saldatori.orders_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final EventClient eventClient;

    public OrderService(OrderRepository orderRepository, EventClient eventClient) {
        this.orderRepository = orderRepository;
        this.eventClient = eventClient;
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public int getTicketsRemaining(RemoteEvent event) {
        int confirmed = orderRepository.sumQuantityByEventIdAndStatus(event.id(), Status.CONFIRMED);
        return event.capacity() - confirmed;
    }

    @Transactional
    public Order placeOrder(Long eventId, OrderRequest request) {
        RemoteEvent event = eventClient.getEvent(eventId);
        int remaining = getTicketsRemaining(event);
        if (request.quantity() > remaining) {
            throw new InsufficientCapacityException(eventId, remaining, request.quantity());
        }

        Order order = new Order();
        order.setEventId(eventId);
        order.setCustomerEmail(request.customerEmail());
        order.setQuantity(request.quantity());
        order.setStatus(Status.CONFIRMED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = getById(orderId);
        if (order.getStatus() != Status.CONFIRMED) {
            throw new InvalidOrderStateException(orderId, "only a CONFIRMED order can be cancelled");
        }

        RemoteEvent event = eventClient.getEvent(order.getEventId());
        if (!event.startsAt().isAfter(OffsetDateTime.now())) {
            throw new InvalidOrderStateException(orderId, "the event has already started");
        }

        order.setStatus(Status.CANCELLED);
        return orderRepository.save(order);
    }
}
