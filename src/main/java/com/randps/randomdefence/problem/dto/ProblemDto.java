package com.randps.randomdefence.problem.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
public class ProblemDto {

    private Integer problemId;

    private String titleKo;

    private Boolean isSolvable;

    private Boolean isPartial;

    private Integer acceptedUserCount;

    private Integer level;

    private Integer votedUserCount;

    private Boolean sprout;

    private Boolean givesNoRating;

    private Boolean isLevelLocked;

    private String averageTries;

    private Boolean official;

    private List<String> tags;

    @Builder
    public ProblemDto(Integer problemId, String titleKo, Boolean isSolvable, Boolean isPartial, Integer acceptedUserCount, Integer level, Integer votedUserCount, Boolean sprout, Boolean givesNoRating, Boolean isLevelLocked, String averageTries, Boolean official, List<String> tags) {
        this.problemId = problemId;
        this.titleKo = titleKo;
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
}
