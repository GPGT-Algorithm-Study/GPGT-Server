package com.randps.randomdefence.domain.roadmap.controller;

import com.randps.randomdefence.domain.roadmap.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roadmap")
public class RoadmapController {

    private final RoadmapService roadmapService;

    /**
     * 로드맵을 생성한다.
     */
    @PostMapping("/create")
    public void create(@Param("lectureId") String lectureId, @Param("name") String name, @Param("description") String description, @Param("difficulty") String difficulty) {
        roadmapService.save(lectureId, name, description, difficulty);
    }

    /**
     * 특정 로드맵을 수정한다.
     */
    @PutMapping("/update")
    public void update(@Param("id") Long id, @Param("lectureId") String lectureId, @Param("name") String name, @Param("description") String description, @Param("difficulty") String difficulty) {
        roadmapService.update(id, lectureId, name, description, difficulty);
    }

    /**
     * 특정 로드맵을 삭제한다.
     */
    @DeleteMapping("/delete")
    public void delete(@Param("id") Long id) {
        roadmapService.delete(id);
    }

}
