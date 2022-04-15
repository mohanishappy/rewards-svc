package com.example.rewardssvc.service;

import com.example.rewardssvc.model.Order;
import com.example.rewardssvc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders() {
        return StreamSupport
                .stream(orderRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public List<Order> searchOrders(Long orderId, Long customerId) {
        if (orderId != null) {
            return orderRepository.findByOrderId(orderId);
        } else if (customerId != null) {
            return orderRepository.findByCustomerId(customerId);
        } else {
            return getOrders();
        }
    }
}
