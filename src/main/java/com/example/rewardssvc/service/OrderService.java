package com.example.rewardssvc.service;

import com.example.rewardssvc.model.Order;
import com.example.rewardssvc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Order service
 * @author MKANAKAL
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public List<Order> searchOrders(Long customerId) {
        if (customerId != null) {
            List<Order> orders = orderRepository.findByCustomerId(customerId);
            if (orders.isEmpty()){
                throw new NoSuchElementException("No orders found");
            }
            return orders;
        } else {
            return getAllOrders();
        }
    }
}
