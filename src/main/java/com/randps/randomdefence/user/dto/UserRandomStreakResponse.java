package com.randps.randomdefence.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserRandomStreakResponse {
    private String bojHandle;

    private String startLevel;

    private String endLevel;

    private Integer todayRandomProblemId;

    private Boolean isTodayRandomSolved;

    private Integer currentRandomStreak;

    private Integer maxRandomStreak;

    @Builder
    public UserRandomStreakResponse(String bojHandle, String startLevel, String endLevel, Integer todayRandomProblemId, Boolean isTodayRandomSolved, Integer currentRandomStreak, Integer maxRandomStreak) {
        this.bojHandle = bojHandle;
        this.startLevel = startLevel;
        this.endLevel = endLevel;
        this.todayRandomProblemId = todayRandomProblemId;
        this.isTodayRandomSolved = isTodayRandomSolved;
        this.currentRandomStreak = currentRandomStreak;
        this.maxRandomStreak = maxRandomStreak;
    }
}
