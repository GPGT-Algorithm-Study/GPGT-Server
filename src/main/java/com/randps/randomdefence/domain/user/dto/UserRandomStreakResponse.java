package com.randps.randomdefence.domain.user.dto;

import com.randps.randomdefence.domain.problem.domain.Problem;
import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import lombok.Builder;
import lombok.Data;

@Data
public class UserRandomStreakResponse {
    private String bojHandle;

    private String startLevel;

    private String endLevel;

    private Boolean isKo;

    private ProblemDto todayRandomProblem;

    private Boolean isTodayRandomSolved;

    private Integer currentRandomStreak;

    private Integer maxRandomStreak;

    @Builder
    public UserRandomStreakResponse(String bojHandle, String startLevel, String endLevel, Boolean isKo, ProblemDto todayRandomProblem, Boolean isTodayRandomSolved, Integer currentRandomStreak, Integer maxRandomStreak) {
        this.bojHandle = bojHandle;
        this.startLevel = startLevel;
        this.endLevel = endLevel;
        this.isKo = isKo;
        this.todayRandomProblem = todayRandomProblem;
        this.isTodayRandomSolved = isTodayRandomSolved;
        this.currentRandomStreak = currentRandomStreak;
        this.maxRandomStreak = maxRandomStreak;
    }

    public UserRandomStreakResponse(UserRandomStreakDto userRandomStreakDto, Problem problem) {
        this.bojHandle = userRandomStreakDto.getBojHandle();
        this.startLevel = userRandomStreakDto.getStartLevel();
        this.endLevel = userRandomStreakDto.getEndLevel();
        this.isKo = userRandomStreakDto.getIsKo();
        this.todayRandomProblem = problem.toDto();
        this.isTodayRandomSolved = userRandomStreakDto.getIsTodayRandomSolved();
        this.currentRandomStreak = userRandomStreakDto.getCurrentRandomStreak();
        this.maxRandomStreak = userRandomStreakDto.getMaxRandomStreak();
    }

    public UserRandomStreakResponse(UserRandomStreakDto userRandomStreakDto, ProblemDto problemDto) {
        this.bojHandle = userRandomStreakDto.getBojHandle();
        this.startLevel = userRandomStreakDto.getStartLevel();
        this.endLevel = userRandomStreakDto.getEndLevel();
        this.isKo = userRandomStreakDto.getIsKo();
        this.todayRandomProblem = problemDto;
        this.isTodayRandomSolved = userRandomStreakDto.getIsTodayRandomSolved();
        this.currentRandomStreak = userRandomStreakDto.getCurrentRandomStreak();
        this.maxRandomStreak = userRandomStreakDto.getMaxRandomStreak();
    }
}
