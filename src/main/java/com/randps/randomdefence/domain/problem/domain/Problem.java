package com.randps.randomdefence.domain.problem.domain;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    public Problem(Long id, Integer problemId, String titleKo, Boolean isSolvable, Boolean isPartial, Integer acceptedUserCount, Integer level, Integer votedUserCount, Boolean sprout, Boolean givesNoRating, Boolean isLevelLocked, String averageTries, Boolean official, List<String> tags) {
        this.id = id;
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
