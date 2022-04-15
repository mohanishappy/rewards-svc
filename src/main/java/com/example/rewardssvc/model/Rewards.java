package com.example.rewardssvc.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Rewards {

    private List<RewardsEntry> monthlyRewards;
    private Long totalRewardPoints;
}
