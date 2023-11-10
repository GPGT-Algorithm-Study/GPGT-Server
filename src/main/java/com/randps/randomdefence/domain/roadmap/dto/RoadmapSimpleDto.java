package com.randps.randomdefence.domain.roadmap.dto;

import com.randps.randomdefence.domain.roadmap.domain.Roadmap;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class RoadmapSimpleDto {

    private Long id;

    private String lectureId;

    private String name;

    private List<RoadmapTagDto> tags;

    @Builder
    public RoadmapSimpleDto(Long id, String lectureId, String name, List<RoadmapTagDto> tags) {
        this.id = id;
        this.lectureId = lectureId;
        this.name = name;
        this.tags = tags;
    }

    public RoadmapSimpleDto(Roadmap roadmap, List<RoadmapTagDto> roadmapTags) {
        this.id = roadmap.getId();
        this.lectureId = roadmap.getLectureId();
        this.name = roadmap.getName();
        this.tags = roadmapTags;
    }
}
