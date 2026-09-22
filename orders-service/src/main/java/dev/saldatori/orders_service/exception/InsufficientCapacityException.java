package dev.saldatori.orders_service.exception;

public class InsufficientCapacityException extends RuntimeException {
    public InsufficientCapacityException(Long eventId, int remaining, int requested) {
        super("Event " + eventId + " has " + remaining + " ticket(s) remaining, requested " + requested);
    }
}
