package com.example.rewardssvc.repository;

import com.example.rewardssvc.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void givenOrderRepository_whenSaveAndRetrieveEntity_thenOK() {
        Order orderEntity = orderRepository.save(Order.builder().build());
        Optional<Order> foundEntity = orderRepository.findById(orderEntity.getOrderId());

        assertTrue(foundEntity.isPresent());
        assertNotNull(foundEntity.get());
        assertEquals(orderEntity.getOrderId(), foundEntity.get().getOrderId());
    }

    @Test
    public void givenOrderRepository_withoutSaveAndRetrieveEntity_thenFail() {
        final Long customerId = 125L;
        Optional<Order> foundEntity = orderRepository.findById(customerId);

        assertNotNull(foundEntity);
        assertFalse(foundEntity.isPresent());
    }

    @Test
    public void givenOrderRepository_whenSaveAndRetrieveEntityWithCustomerId_thenOK() {
        final Long customerId = 125L;
        orderRepository.save(Order.builder().customerId(customerId).build());
        List<Order> foundEntity = orderRepository.findByCustomerId(customerId);

        assertFalse(foundEntity.isEmpty());
        assertNotNull(foundEntity.get(0));
        assertEquals(customerId, foundEntity.get(0).getCustomerId());
    }

    @Test
    public void givenOrderRepository_whenSaveAndRetrieveEntityWithDifferentCustomerId_thenFail() {
        final Long customer1Id = 125L;
        final Long customer2Id = 126L;
        orderRepository.save(Order.builder().customerId(customer1Id).build());
        List<Order> foundEntity = orderRepository.findByCustomerId(customer2Id);

        assertNotNull(foundEntity);
        assertTrue(foundEntity.isEmpty());
    }
}
