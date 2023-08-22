package com.randps.randomdefence.domain.statistics.dto;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class PointBarDto {

    private User user;

    private Integer dailyEarningPoint;

    private Integer weeklyEarningPoint;

    private Integer totalEarningPoint;

    @Builder
    public PointBarDto(User user, Integer dailyEarningPoint, Integer weeklyEarningPoint, Integer totalEarningPoint) {
        this.user = user;
        this.dailyEarningPoint = dailyEarningPoint;
        this.weeklyEarningPoint = weeklyEarningPoint;
        this.totalEarningPoint = totalEarningPoint;
    }
}
