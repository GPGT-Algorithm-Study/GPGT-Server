package com.randps.randomdefence.domain.statistics.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointBarPair {

    // User
    private String notionId;

    private String emoji;

    // UserStatistics
    private Integer dailyEarningPoint;

    private Integer weeklyEarningPoint;

    private Integer totalEarningPoint;

    @Builder
    public PointBarPair(String notionId, String emoji, Integer dailyEarningPoint, Integer weeklyEarningPoint, Integer totalEarningPoint) {
        this.notionId = notionId;
        this.emoji = emoji;
        this.dailyEarningPoint = dailyEarningPoint;
        this.weeklyEarningPoint = weeklyEarningPoint;
        this.totalEarningPoint = totalEarningPoint;
    }
}
