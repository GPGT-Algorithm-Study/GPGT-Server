package com.randps.randomdefence.userSolvedProblem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SolvedProblemDto {

    private Integer problemId;

    private String title;

    private String dateTime;

    private Integer tier;

    private List<String> tags;

    @Builder
    public SolvedProblemDto(Integer problemId, String title, String dateTime) {
        this.problemId = problemId;
        this.title = title;
        this.dateTime = dateTime;
        this.tier = 0;
        this.tags = null;
    }
}
