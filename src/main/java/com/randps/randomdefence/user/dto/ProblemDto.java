package com.randps.randomdefence.user.dto;

import com.randps.randomdefence.recommendation.dto.RecommendationResponse;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ProblemDto {
    public Integer problemId;
    public String title;
    public Boolean isSolvable;
    public Boolean isPartial;
    public Integer acceptedUserCount;
    public String level;
    public Boolean sprout;
    public Boolean givesNoRating;
    public String averageTries;
    public Boolean official;
    public ArrayList<Object> tags;

    @Builder
    public ProblemDto(Integer problemId, String title, Boolean isSolvable, Boolean isPartial, Integer acceptedUserCount, String level, Boolean sprout, Boolean givesNoRating, String averageTries, Boolean official, ArrayList<Object> tags) {
        this.problemId = problemId;
        this.title = title;
        this.isSolvable = isSolvable;
        this.isPartial = isPartial;
        this.acceptedUserCount = acceptedUserCount;
        this.level = level;
        this.sprout = sprout;
        this.givesNoRating = givesNoRating;
        this.averageTries = averageTries;
        this.official = official;
        this.tags = tags;
    }
}
