package com.randps.randomdefence.domain.roadmap.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoadmapTagDto {

    private Long roadmapId;

    private String name;

    private Long count;

}
