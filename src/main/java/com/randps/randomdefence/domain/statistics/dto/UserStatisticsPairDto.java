package com.randps.randomdefence.domain.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserStatisticsPairDto {

    // User
    private String bojHandle;

    private String notionId;

    private String profileImg;

    private String emoji;

    private Integer point;

    // UserStatistics
    private Integer weeklySolvedProblemCount;

    private Integer weeklyEarningPoint;

    @Builder
    public UserStatisticsPairDto(String bojHandle, String notionId, String profileImg, String emoji, Integer point, Integer weeklySolvedProblemCount, Integer weeklyEarningPoint) {
        this.bojHandle = bojHandle;
        this.notionId = notionId;
        this.profileImg = profileImg;
        this.emoji = emoji;
        this.point = point;
        this.weeklySolvedProblemCount = weeklySolvedProblemCount;
        this.weeklyEarningPoint = weeklyEarningPoint;
    }

    public UserStatisticsPairDto() {}

}
