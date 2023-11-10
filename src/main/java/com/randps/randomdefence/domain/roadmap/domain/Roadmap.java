package com.randps.randomdefence.domain.roadmap.domain;

import com.randps.randomdefence.domain.roadmap.dto.RoadmapDto;
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
@Table(name = "RD_ROADMAP")
@Entity
public class Roadmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lectureId;

    private String name;

    private String description;

    private String difficulty;

    private Long totalProblemCount;

    @Builder
    public Roadmap(String lectureId, String name, String description, String difficulty, Long totalProblemCount) {
        this.lectureId = lectureId;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.totalProblemCount = totalProblemCount;
    }

    public void update(String lectureId, String name, String description, String difficulty) {
        this.lectureId = lectureId;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
    }

    public void increaseTotalProblemCount() {
        this.totalProblemCount++;
    }

    public void decreaseTotalProblemCount() {
        if (this.totalProblemCount > 0)
            this.totalProblemCount--;
    }

    public RoadmapDto toDto() {
        return RoadmapDto.builder()
                .id(this.id)
                .lectureId(this.lectureId)
                .name(this.name)
                .description(this.description)
                .difficulty(this.difficulty)
                .totalProblemCount(this.totalProblemCount)
                .build();
    }

}
