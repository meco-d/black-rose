package dev.saldatori.orders_service.exception;

public class InvalidOrderStateException extends RuntimeException {
    public InvalidOrderStateException(Long orderId, String reason) {
        super("Order " + orderId + " cannot be cancelled: " + reason);
    }
}
