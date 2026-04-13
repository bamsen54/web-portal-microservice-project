package com.wigell.sushi.controller;

import com.wigell.sushi.dto.OrderDTO;
import com.wigell.sushi.entity.Order;
import com.wigell.sushi.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public List<OrderDTO> getOrdersByCustomerId(@RequestParam int customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody Order order) {
        OrderDTO saved = orderService.createOrder(order);
        return ResponseEntity.created(URI.create("/api/v1/orders/" + saved.getId())).body(saved);
    }
}