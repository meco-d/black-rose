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
@RequestMapping("/events")
public class EventController {
    private final EventClient eventClient;
    private final OrderService orderService;

    public EventController(EventClient eventClient, OrderService orderService) {
        this.eventClient = eventClient;
        this.orderService = orderService;
    }

    @GetMapping("/{id}/availability")
    public EventAvailabilityResponse getAvailability(@PathVariable Long id) {
        RemoteEvent event = eventClient.getEvent(id);
        int ticketsRemaining = orderService.getTicketsRemaining(event);
        return EventAvailabilityResponse.from(event, ticketsRemaining);
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<OrderResponse> placeOrder(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        Order order = orderService.placeOrder(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
    }
}
