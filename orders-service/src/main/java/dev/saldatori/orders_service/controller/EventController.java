package dev.saldatori.orders_service.controller;

import dev.saldatori.orders_service.model.dto.EventDetailResponse;
import dev.saldatori.orders_service.model.dto.EventRequest;
import dev.saldatori.orders_service.model.dto.EventResponse;
import dev.saldatori.orders_service.model.dto.OrderRequest;
import dev.saldatori.orders_service.model.dto.OrderResponse;
import dev.saldatori.orders_service.model.entity.Event;
import dev.saldatori.orders_service.model.entity.Order;
import dev.saldatori.orders_service.service.EventService;
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

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final OrderService orderService;

    public EventController(EventService eventService, OrderService orderService) {
        this.eventService = eventService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest request) {
        Event event = new Event();
        event.setName(request.name());
        event.setStartsAt(request.startsAt());
        event.setCapacity(request.capacity());

        Event saved = eventService.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(EventResponse.from(saved));
    }

    @GetMapping
    public List<EventResponse> findAll() {
        return eventService.findAll().stream().map(EventResponse::from).toList();
    }

    @GetMapping("/{id}")
    public EventDetailResponse findById(@PathVariable Long id) {
        Event event = eventService.getById(id);
        int ticketsRemaining = orderService.getTicketsRemaining(event);
        return EventDetailResponse.from(event, ticketsRemaining);
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<OrderResponse> placeOrder(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        Order order = orderService.placeOrder(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
    }
}
