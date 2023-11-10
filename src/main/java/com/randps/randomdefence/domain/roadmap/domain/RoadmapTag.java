package com.randps.randomdefence.domain.roadmap.domain;

import com.randps.randomdefence.domain.roadmap.dto.RoadmapTagDto;
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
@Table(name = "RD_ROADMAP_TAG")
@Entity
public class RoadmapTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roadmapId;

    private String name;

    private Long count;

    @Builder
    public RoadmapTag(Long roadmapId, String name) {
        this.roadmapId = roadmapId;
        this.name = name;
        this.count = 0L;
    }

    public void update(Long roadmapId, String name, Long count) {
        this.roadmapId = roadmapId;
        this.name = name;
        this.count = count;
    }

    public void incrementCount() {
        this.count++;
    }

    public boolean decrementCount() {
        this.count--;
        return this.count <= 0;
    }

    public RoadmapTagDto toDto() {
        return RoadmapTagDto.builder()
                .roadmapId(this.roadmapId)
                .name(this.name)
                .count(this.count)
                .build();
    }

}
