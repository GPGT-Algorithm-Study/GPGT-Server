package com.randps.randomdefence.domain.statistics.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
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
@Table(name = "RD_STATISTICS_PROBLEM")
@Entity
public class ProblemStatistics extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer problemId;

    private Long solvedCount;

    @Builder
    public ProblemStatistics(Integer problemId, Long solvedCount) {
        this.problemId = problemId;
        this.solvedCount = solvedCount;
    }

    public void incrementSolvedCount() {
        this.solvedCount++;
    }

}
