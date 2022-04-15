package com.example.rewardssvc.service;

import com.example.rewardssvc.model.Customer;
import com.example.rewardssvc.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CustomerServiceTests {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        String customerName = "alex";
        List<Customer> alex = List.of(Customer.builder().name(customerName).build());
        Mockito.when(customerRepository.findByName(customerName)).thenReturn(alex);
        Mockito.when(customerRepository.findByCustomerId(any(Long.class))).thenReturn(alex);
        Mockito.when(customerRepository.findAll()).thenReturn(alex);
    }

    @Test
    public void getAllCustomers_test(){
        List<Customer> allCustomers = customerService.getAllCustomers();

        assertNotNull(allCustomers);
        assertFalse(allCustomers.isEmpty());
        assertEquals(1, allCustomers.size());
        assertEquals("alex", allCustomers.get(0).getName());
    }
}
