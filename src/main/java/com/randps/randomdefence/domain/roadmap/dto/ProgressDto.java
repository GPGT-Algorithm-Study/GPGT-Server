package com.randps.randomdefence.domain.roadmap.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgressDto {

    private Long roadmapId;

    private String name;

    private Double progress;

}
