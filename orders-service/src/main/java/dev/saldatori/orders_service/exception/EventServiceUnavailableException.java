package dev.saldatori.orders_service.exception;

public class EventServiceUnavailableException extends RuntimeException {
    public EventServiceUnavailableException(Throwable cause) {
        super("events-service is unreachable", cause);
    }
}
