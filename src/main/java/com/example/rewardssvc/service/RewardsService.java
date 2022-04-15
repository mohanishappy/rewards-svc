package com.example.rewardssvc.service;

import com.example.rewardssvc.model.Order;
import com.example.rewardssvc.model.Rewards;
import com.example.rewardssvc.model.RewardsEntry;
import com.example.rewardssvc.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
@Slf4j
public class RewardsService {

    private final OrderRepository orderRepository;

    @Autowired
    public RewardsService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private long getRewardPoints(double purchaseAmount) {
        long rewardPoints = 0;
        if (purchaseAmount > 100) {
            rewardPoints += purchaseAmount - 100;
        }
        if (purchaseAmount > 50) {
            rewardPoints += purchaseAmount - 50;
        }
        return rewardPoints;
    }

    public Rewards getRewardsByCustomer(Long customerId) {

        List<Order> byCustomerId = orderRepository.findByCustomerId(customerId);

        Map<YearMonth, Long> rewardsByYearMonth = byCustomerId.stream()
                .collect(groupingBy(m -> YearMonth.from(m.getPurchaseDate()),
                        summingLong(m -> getRewardPoints(m.getPurchaseAmount()))));

        Long totalRewards = rewardsByYearMonth.values().stream().mapToLong(d -> d).sum();

        List<RewardsEntry> rewardsEntries = rewardsByYearMonth.entrySet().stream()
                .map(e -> RewardsEntry.builder()
                        .month(e.getKey().getMonth().name())
                        .year(String.valueOf(e.getKey().getYear()))
                        .rewardPoints(e.getValue()).build())
                .collect(toList());

        return Rewards.builder()
                .monthlyRewards(rewardsEntries)
                .totalRewardPoints(totalRewards)
                .build();
    }
}
