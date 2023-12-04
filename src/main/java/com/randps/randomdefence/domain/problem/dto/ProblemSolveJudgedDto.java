package com.randps.randomdefence.domain.problem.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class ProblemSolveJudgedDto {


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

    private Boolean isSolved;

    @Builder
    public ProblemSolveJudgedDto(Integer problemId, Boolean isSolved, String titleKo, Boolean isSolvable, Boolean isPartial, Integer acceptedUserCount, Integer level, Integer votedUserCount, Boolean sprout, Boolean givesNoRating, Boolean isLevelLocked, String averageTries, Boolean official, List<String> tags) {
        this.problemId = problemId;
        this.isSolved = isSolved;
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

    public ProblemSolveJudgedDto(ProblemDto problemDto, Boolean isSolved) {
        this.problemId = problemDto.getProblemId();
        this.isSolved = isSolved;
        this.titleKo = problemDto.getTitleKo();
        this.isSolvable = problemDto.getIsSolvable();
        this.isPartial = problemDto.getIsPartial();
        this.acceptedUserCount = problemDto.getAcceptedUserCount();
        this.level = problemDto.getLevel();
        this.votedUserCount = problemDto.getVotedUserCount();
        this.sprout = problemDto.getSprout();
        this.givesNoRating = problemDto.getGivesNoRating();
        this.isLevelLocked = problemDto.getIsLevelLocked();
        this.averageTries = problemDto.getAverageTries();
        this.official = problemDto.getOfficial();
        this.tags = problemDto.getTags();
        this.point = problemDto.getLevel();
    }

    public ProblemSolveJudgedDto() {
        this.problemId = 0;
        this.isSolved = false;
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
