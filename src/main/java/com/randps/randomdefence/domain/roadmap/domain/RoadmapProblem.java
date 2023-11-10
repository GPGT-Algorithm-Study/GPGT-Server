package com.randps.randomdefence.domain.roadmap.domain;

import com.randps.randomdefence.domain.roadmap.dto.RoadmapProblemDto;
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
@Table(name = "RD_ROADMAP_PROBLEM")
@Entity
public class RoadmapProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roadmapId;

    private Integer problemId;

    private Long week;

    private Long index;

    @Builder
    public RoadmapProblem(Long roadmapId, Integer problemId, Long week, Long index) {
        this.roadmapId = roadmapId;
        this.problemId = problemId;
        this.week = week;
        this.index = index;
    }

    public void update(Long roadmapId, Integer problemId, Long week, Long index) {
        this.roadmapId = roadmapId;
        this.problemId = problemId;
        this.week = week;
        this.index = index;
    }

    public RoadmapProblemDto toDto() {
        return RoadmapProblemDto.builder()
                .id(this.id)
                .roadmapId(this.roadmapId)
                .problemId(this.problemId)
                .week(this.week)
                .index(this.index)
                .build();
    }
}
