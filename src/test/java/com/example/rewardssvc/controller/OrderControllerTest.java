package com.example.rewardssvc.controller;

import com.example.rewardssvc.model.Order;
import com.example.rewardssvc.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setUp(){
        Long id1 = 131L;
        Long id2 = 132L;
        Order order1 = Order.builder().orderId(id1).customerId(id1).build();
        Order order2 = Order.builder().orderId(id2).customerId(id2).build();

        Mockito.when(orderService.getOrder(id1))
                .thenReturn(order1);
        doThrow(NoSuchElementException.class)
                .when(orderService).getOrder(125L);

        Mockito.when(orderService.searchOrders(id1))
                .thenReturn(List.of(order1));

        Mockito.when(orderService.searchOrders(null))
                .thenReturn(List.of(order1,order2));
    }

    @Test
    public void givenOrders_whenGetOrder_thenStatus200()
            throws Exception {

        mvc.perform(get("/api/v1/orders?orderId=131")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId", is(131)))
                .andExpect(jsonPath("$.customerId", is(131)));
    }

    @Test
    public void givenOrders_whenGetOrder_thenStatusNotFound()
            throws Exception {

        mvc.perform(get("/api/v1/orders?orderId=125")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("No data found")));
    }

    @Test
    public void givenOrders_whenSearchOrdersWithParam_thenStatusOk()
            throws Exception {

        mvc.perform(get("/api/v1/orders/search?customerId=131")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(131)));
    }

    @Test
    public void givenOrders_whenSearchOrdersEmptyParam_thenStatusOk()
            throws Exception {

        mvc.perform(get("/api/v1/orders/search?customerId=")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].customerId", is(131)))
                .andExpect(jsonPath("$[1].customerId", is(132)));
    }

    @Test
    public void givenOrders_whenSearchOrdersNoParam_thenStatusOk()
            throws Exception {

        mvc.perform(get("/api/v1/orders/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].customerId", is(131)))
                .andExpect(jsonPath("$[1].customerId", is(132)));
    }
}
