package com.randps.randomdefence.domain.problem.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

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

    private Integer point;
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
        this.point = level;
    }

    public ProblemDto() {
        this.problemId = 0;
        this.titleKo = "";
        this.isSolvable = false;
        this.isPartial = false;
        this.acceptedUserCount = 0;
        this.level = 0;
        this.votedUserCount = 0;
        this.sprout = false;
        this.givesNoRating = false;
        this.isLevelLocked = false;
        this.averageTries = "";
        this.official = false;
        this.tags = new ArrayList<String>();
        this.point = 0;
    }

    public void toDoublePoint() {
        this.point *= 2;
    }
}
