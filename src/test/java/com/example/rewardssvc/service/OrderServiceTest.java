package com.example.rewardssvc.service;

import com.example.rewardssvc.model.Order;
import com.example.rewardssvc.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        Order order1 = Order.builder().orderId(1L).customerId(1L).build();
        Order order2 = Order.builder().orderId(2L).customerId(2L).build();

        Mockito.when(orderRepository.findByCustomerId(1L))
                .thenReturn(List.of(order1));

        Mockito.when(orderRepository.findByCustomerId(2L))
                .thenReturn(emptyList());

        Mockito.when(orderRepository.findById(order1.getOrderId()))
                .thenReturn(Optional.of(order1));
        doThrow(NoSuchElementException.class)
                .when(orderRepository).findById(61L);

        Mockito.when(orderRepository.findAll())
                .thenReturn(List.of(order1,order2));
    }

    @Test
    public void getAllOrders_test(){
        Long customerId = 1L;
        List<Order> allOrders = orderService.getAllOrders();

        assertNotNull(allOrders);
        assertFalse(allOrders.isEmpty());
        assertEquals(2, allOrders.size());
        assertEquals(customerId, allOrders.get(0).getCustomerId());
        verify(orderRepository).findAll();
    }

    @Test
    public void getOrderForOrderId_test(){
        Long orderId = 1L;
        Long customerId = 1L;
        Order order = orderService.getOrder(orderId);

        assertNotNull(order);
        assertEquals(orderId, order.getOrderId());
        assertEquals(customerId, order.getCustomerId());
        verify(orderRepository).findById(orderId);
    }

    @Test
    public void getOrdersForOrderIdNotExist_test(){
        Exception exception = assertThrows(NoSuchElementException.class, () -> orderService.getOrder(61L));

        assertNotNull(exception);
        assertNull(exception.getLocalizedMessage());
        verify(orderRepository).findById(any());
    }

    @Test
    public void searchOrdersForOrderName_test(){
        Long customerId = 1L;
        List<Order> allOrders = orderService.searchOrders(customerId);

        assertNotNull(allOrders);
        assertFalse(allOrders.isEmpty());
        assertEquals(1, allOrders.size());
        assertEquals(customerId, allOrders.get(0).getCustomerId());
        verify(orderRepository).findByCustomerId(customerId);
    }

    @Test
    public void searchOrdersForOrderIdNotExists_test(){
        Exception exception = assertThrows(NoSuchElementException.class, () -> orderService.searchOrders(2L));

        assertNotNull(exception);
        assertEquals("No orders found", exception.getLocalizedMessage());
        verify(orderRepository).findByCustomerId(any());
    }

    @Test
    public void searchOrdersForOrderNameAsNull_test(){
        Long customerId = 1L;
        List<Order> allOrders = orderService.searchOrders(null);

        assertNotNull(allOrders);
        assertFalse(allOrders.isEmpty());
        assertEquals(2, allOrders.size());
        assertEquals(customerId, allOrders.get(0).getCustomerId());
        verify(orderRepository).findAll();
    }
}
