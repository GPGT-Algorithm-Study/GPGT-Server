package com.randps.randomdefence.component.parser.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserScrapingInfoDto {

    private Integer tier;
    private String profileImg;
    private Integer currentStreak;
    private Integer totalSolved;
    private Boolean isTodaySolved;

    private Integer todaySolvedProblemCount;

    @Builder
    public UserScrapingInfoDto(Integer tier, String profileImg, Integer currentStreak, Integer totalSolved, Boolean isTodaySolved, Integer todaySolvedProblemCount) {
        this.tier = tier;
        this.profileImg = profileImg;
        this.currentStreak = currentStreak;
        this.totalSolved = totalSolved;
        this.isTodaySolved = isTodaySolved;
        this.todaySolvedProblemCount = todaySolvedProblemCount;
    }
}
