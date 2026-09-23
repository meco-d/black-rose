package dev.saldatori.orders_service.controller;

import dev.saldatori.orders_service.client.EventClient;
import dev.saldatori.orders_service.client.RemoteEvent;
import dev.saldatori.orders_service.model.dto.EventAvailabilityResponse;
import dev.saldatori.orders_service.model.dto.OrderRequest;
import dev.saldatori.orders_service.model.dto.OrderResponse;
import dev.saldatori.orders_service.model.entity.Order;
import dev.saldatori.orders_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final EventClient eventClient;

    public OrderController(OrderService orderService, EventClient eventClient) {
        this.orderService = orderService;
        this.eventClient = eventClient;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        Order order = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return OrderResponse.from(orderService.getById(id));
    }

    @PostMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable Long id) {
        return OrderResponse.from(orderService.cancelOrder(id));
    }

    @GetMapping("/availability/{eventId}")
    public EventAvailabilityResponse getAvailability(@PathVariable Long eventId) {
        RemoteEvent event = eventClient.getEvent(eventId);
        int ticketsRemaining = orderService.getTicketsRemaining(event);
        return EventAvailabilityResponse.from(event, ticketsRemaining);
    }
}
