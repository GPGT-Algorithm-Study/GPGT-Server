package com.randps.randomdefence.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDailyStreakResponse {
    public Boolean hasSolved;
    public Long solvedTotal;
    public List<ProblemDto> solvedList;
}
