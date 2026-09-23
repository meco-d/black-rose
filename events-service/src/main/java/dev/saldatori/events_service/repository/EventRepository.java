package dev.saldatori.events_service.repository;

import dev.saldatori.events_service.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
