package com.randps.randomdefence.problem.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import com.randps.randomdefence.problem.dto.ProblemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "RD_PROBLEM")
@Entity
public class Problem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ElementCollection
    private List<String> tags;

    @Builder
    public Problem(Integer problemId, String titleKo, Boolean isSolvable, Boolean isPartial, Integer acceptedUserCount, Integer level, Integer votedUserCount, Boolean sprout, Boolean givesNoRating, Boolean isLevelLocked, String averageTries, Boolean official, List<String> tags) {
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

    public ProblemDto toDto() {
        return ProblemDto.builder()
                .problemId(this.getProblemId())
                .titleKo(this.getTitleKo())
                .isSolvable(this.getIsSolvable())
                .isPartial(this.getIsPartial())
                .acceptedUserCount(this.getAcceptedUserCount())
                .level(this.getLevel())
                .votedUserCount(this.getVotedUserCount())
                .sprout(this.getSprout())
                .givesNoRating(this.getGivesNoRating())
                .isLevelLocked(this.getIsLevelLocked())
                .averageTries(this.getAverageTries())
                .official(this.getOfficial())
                .tags(this.getTags())
                .build();
    }
}
