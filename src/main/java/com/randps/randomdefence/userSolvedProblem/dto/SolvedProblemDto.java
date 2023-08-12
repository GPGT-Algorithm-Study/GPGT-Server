package com.randps.randomdefence.userSolvedProblem.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SolvedProblemDto {

    private Integer problemId;

    private String title;

    private String dateTime;

    @Builder
    public SolvedProblemDto(Integer problemId, String title, String dateTime) {
        this.problemId = problemId;
        this.title = title;
        this.dateTime = dateTime;
    }
}
