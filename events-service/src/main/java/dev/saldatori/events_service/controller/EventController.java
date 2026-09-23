package dev.saldatori.events_service.controller;

import dev.saldatori.events_service.model.dto.EventRequest;
import dev.saldatori.events_service.model.dto.EventResponse;
import dev.saldatori.events_service.model.entity.Event;
import dev.saldatori.events_service.service.EventService;
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

    public EventController(EventService eventService) {
        this.eventService = eventService;
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
    public EventResponse findById(@PathVariable Long id) {
        return EventResponse.from(eventService.getById(id));
    }
}
