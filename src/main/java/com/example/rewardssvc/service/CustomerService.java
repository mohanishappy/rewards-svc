package com.example.rewardssvc.service;

import com.example.rewardssvc.model.Customer;
import com.example.rewardssvc.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Customer service
 * @author MKANAKAL
 */
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

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow();
    }
    public List<Customer> searchCustomers(String customerName) {
        if (StringUtils.isNotEmpty(customerName)) {
            List<Customer> customers = customerRepository.findByName(customerName);
            if (customers.isEmpty()){
                throw new NoSuchElementException("No customers found");
            }
            return customers;
        } else {
            return getAllCustomers();
        }
    }
}
