package com.randps.randomdefence.component.crawler.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BojProblemPair {
    public Integer problemId;
    public String title;
    public String dateTime;

    @Builder
    public BojProblemPair(Integer problemId, String title, String dateTime) {
        this.problemId = problemId;
        this.title = title;
        this.dateTime = dateTime;
    }
}
