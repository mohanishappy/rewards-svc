package com.example.rewardssvc.repository;

import com.example.rewardssvc.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void givenCustomerRepository_whenSaveAndRetrieveEntity_thenOK() {
        Customer customerEntity = customerRepository.save(Customer.builder().build());
        Optional<Customer> foundEntity = customerRepository.findById(customerEntity.getCustomerId());

        assertTrue(foundEntity.isPresent());
        assertNotNull(foundEntity.get());
        assertEquals(customerEntity.getCustomerId(), foundEntity.get().getCustomerId());
    }

    @Test
    public void givenCustomerRepository_whenSaveAndRetrieveEntityWithName_thenOK() {
        final String customerName = "John Doe" ;
        customerRepository.save(Customer.builder().name(customerName).build());
        List<Customer> foundEntity = customerRepository.findByName(customerName);

        assertFalse(foundEntity.isEmpty());
        assertNotNull(foundEntity.get(0));
        assertEquals(customerName, foundEntity.get(0).getName());
    }
}
