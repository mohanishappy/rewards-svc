package com.example.rewardssvc.repository;

import com.example.rewardssvc.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCustomerId(Long customerId);
}
