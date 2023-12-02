package com.randps.randomdefence.domain.roadmap.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoadmapDto {

    private Long id;

    private String lectureId;

    private String name;

    private String classification;

    private String description;

    private String difficulty;

    private Long totalProblemCount;

}
