package com.randps.randomdefence.domain.user.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class SolvedProblemDto {

    private Integer problemId;

    private String title;

    private String dateTime;

    private Integer tier;

    private List<String> tags;

    private String language;

    private Integer point;

    @Builder
    public SolvedProblemDto(Integer problemId, String title, String dateTime, String language) {
        this.problemId = problemId;
        this.title = title;
        this.dateTime = dateTime;
        this.tier = 0;
        this.tags = null;
        this.language = language;
        this.point = 0;
    }
}
