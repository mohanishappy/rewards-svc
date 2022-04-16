package com.example.rewardssvc.controller;

import com.example.rewardssvc.model.Order;
import com.example.rewardssvc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Order Controller
 * @author MKANAKAL
 */
@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Order getOrder(@RequestParam Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping(path = "/search")
    public List<Order> searchOrders(@RequestParam(required = false) Long customerId) {
        return orderService.searchOrders(customerId);
    }
}
