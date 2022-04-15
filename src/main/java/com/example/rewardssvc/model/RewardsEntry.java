package com.example.rewardssvc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RewardsEntry {
    private String month;
    private String year;
    private Long rewardPoints;
}
