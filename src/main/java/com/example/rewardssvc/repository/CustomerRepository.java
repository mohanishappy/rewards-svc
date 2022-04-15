package com.example.rewardssvc.repository;

import com.example.rewardssvc.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByCustomerId(Long customerId);
    List<Customer> findByName(String customerName);
}
