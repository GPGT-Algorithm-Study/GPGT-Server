package com.randps.randomdefence.recommendation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
public class RecommendationResponse {
    public Integer problemId;
    public String titleKo;
    public Object titles;
    public Boolean isSolvable;
    public Boolean isPartial;
    public Integer acceptedUserCount;
    public String level;
    public Integer votedUserCount;
    public Boolean sprout;
    public Boolean givesNoRating;
    public Boolean isLevelLocked;
    public String averageTries;
    public Boolean official;
    public ArrayList<Object> tags;

    @Builder
    public RecommendationResponse(Integer problemId, String titleKo, Object titles, Boolean isSolvable, Boolean isPartial, Integer acceptedUserCount, String level, Integer votedUserCount, Boolean sprout, Boolean givesNoRating, Boolean isLevelLocked, String averageTries, Boolean official, ArrayList<Object> tags) {
        this.problemId = problemId;
        this.titleKo = titleKo;
        this.titles = titles;
        this.isSolvable = isSolvable;
        this.isPartial = isPartial;
        this.acceptedUserCount = acceptedUserCount;
        this.level = level;
        this.votedUserCount = votedUserCount;
        this.sprout = sprout;
        this.givesNoRating = givesNoRating;
        this.isLevelLocked = isLevelLocked;
        this.averageTries = averageTries;
        this.official = official;
        this.tags = tags;
    }

    public RecommendationResponse() {
        this.problemId = null;
        this.titleKo = null;
        this.titles = null;
        this.isSolvable = null;
        this.isPartial = null;
        this.acceptedUserCount = null;
        this.level = null;
        this.votedUserCount = null;
        this.sprout = null;
        this.givesNoRating = null;
        this.isLevelLocked = null;
        this.averageTries = null;
        this.official = null;
        this.tags = null;
    }
}
