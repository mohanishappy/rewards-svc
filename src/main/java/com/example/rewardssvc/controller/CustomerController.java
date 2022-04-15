package com.example.rewardssvc.controller;

import com.example.rewardssvc.model.Customer;
import com.example.rewardssvc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/search")
    public List<Customer> searchCustomers(@RequestParam(required = false) Long customerId,
                                          @RequestParam(required = false) String customerName) {
        return customerService.searchCustomers(customerId, customerName);
    }
}
