package com.example.rewardssvc.repository;

import com.example.rewardssvc.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByOrderId(Long orderId);

}
