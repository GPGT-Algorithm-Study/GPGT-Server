package com.randps.randomdefence.domain.roadmap.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoadmapProblemDto {

    private Long id;

    private Long roadmapId;

    private Integer problemId;

    private Long week;

    private Long index;

    public RoadmapProblemDto(Long id, Long roadmapId, Integer problemId, Long week, Long index) {
        this.id = id;
        this.roadmapId = roadmapId;
        this.problemId = problemId;
        this.week = week;
        this.index = index;
    }
}
