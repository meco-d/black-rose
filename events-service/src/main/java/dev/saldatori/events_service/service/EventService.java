package dev.saldatori.events_service.service;

import dev.saldatori.events_service.exception.EventNotFoundException;
import dev.saldatori.events_service.model.entity.Event;
import dev.saldatori.events_service.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }
}
