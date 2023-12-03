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

    private String classification;

    private List<RoadmapTagDto> tags;

    @Builder
    public RoadmapSimpleDto(Long id, String lectureId, String name, String classification, List<RoadmapTagDto> tags) {
        this.id = id;
        this.lectureId = lectureId;
        this.name = name;
        this.classification = classification;
        this.tags = tags;
    }

    public RoadmapSimpleDto(Roadmap roadmap, List<RoadmapTagDto> roadmapTags) {
        this.id = roadmap.getId();
        this.lectureId = roadmap.getLectureId();
        this.name = roadmap.getName();
        this.classification = roadmap.getClassification();
        this.tags = roadmapTags;
    }
}
