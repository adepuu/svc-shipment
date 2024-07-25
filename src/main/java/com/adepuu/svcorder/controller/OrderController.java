package com.adepuu.svcorder.controller;

import com.adepuu.svcorder.dto.CreateOrderDTO;
import com.adepuu.svcorder.entity.Order;
import com.adepuu.svcorder.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTO req) {
        return ResponseEntity.ok(orderService.createOrder(req));
    }
}
