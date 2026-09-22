package dev.saldatori.orders_service.repository;

import dev.saldatori.orders_service.model.entity.Order;
import dev.saldatori.orders_service.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COALESCE(SUM(o.quantity), 0) FROM Order o WHERE o.eventId = :eventId AND o.status = :status")
    int sumQuantityByEventIdAndStatus(@Param("eventId") Long eventId, @Param("status") Status status);
}
