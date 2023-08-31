package com.randps.randomdefence.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserRandomStreakDto {
    private String bojHandle;

    private String startLevel;

    private String endLevel;

    private Boolean isKo;

    private Integer todayRandomProblemId;

    private Boolean isTodayRandomSolved;

    private Integer currentRandomStreak;

    private Integer maxRandomStreak;

    @Builder
    public UserRandomStreakDto(String bojHandle, String startLevel, String endLevel, Boolean isKo, Integer todayRandomProblemId, Boolean isTodayRandomSolved, Integer currentRandomStreak, Integer maxRandomStreak) {
        this.bojHandle = bojHandle;
        this.startLevel = startLevel;
        this.endLevel = endLevel;
        this.isKo = isKo;
        this.todayRandomProblemId = todayRandomProblemId;
        this.isTodayRandomSolved = isTodayRandomSolved;
        this.currentRandomStreak = currentRandomStreak;
        this.maxRandomStreak = maxRandomStreak;
    }
}
