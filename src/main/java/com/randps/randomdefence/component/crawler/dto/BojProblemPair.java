package com.randps.randomdefence.component.crawler.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BojProblemPair {
    public Integer problemId;

    public String title;

    public String dateTime;

    public String language;

    @Builder
    public BojProblemPair(Integer problemId, String title, String dateTime, String language) {
        this.problemId = problemId;
        this.title = title;
        this.dateTime = dateTime;
        this.language = language;
    }
}
