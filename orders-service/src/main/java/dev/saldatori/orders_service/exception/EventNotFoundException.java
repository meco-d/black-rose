package dev.saldatori.orders_service.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Long id) {
        super("Event not found: " + id);
    }
}
