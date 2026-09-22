package dev.saldatori.orders_service.repository;

import dev.saldatori.orders_service.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
