package com.example.rewardssvc.service;

import com.example.rewardssvc.model.Customer;
import com.example.rewardssvc.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> searchCustomers(Long customerId, String customerName) {
        if (customerId != null) {
            return customerRepository.findByCustomerId(customerId);
        } else if (StringUtils.isNotEmpty(customerName)) {
            return customerRepository.findByName(customerName);
        } else {
            return getAllCustomers();
        }

    }
}
