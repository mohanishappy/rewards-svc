package com.example.rewardssvc.service;

import com.example.rewardssvc.model.Customer;
import com.example.rewardssvc.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return StreamSupport
                .stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Customer getCustomer(Long customerId) {
        return customerRepository.findByCustomerId(customerId);
    }
}
