package dev.saldatori.orders_service.controller;

import dev.saldatori.orders_service.model.dto.OrderResponse;
import dev.saldatori.orders_service.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return OrderResponse.from(orderService.getById(id));
    }

    @PostMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable Long id) {
        return OrderResponse.from(orderService.cancelOrder(id));
    }
}
