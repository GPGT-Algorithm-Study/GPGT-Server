package com.randps.randomdefence.domain.roadmap.controller;

import com.randps.randomdefence.domain.roadmap.service.RoadmapProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roadmap/problem")
public class RoadmapProblemController {

    private final RoadmapProblemService roadmapProblemService;

    /**
     * 특정 로드맵의 특정 주차에 문제를 추가한다.
     */
    @PostMapping("/add")
    public void saveRoadmapProblem(@Param("roadmapId") Long roadmapId, @Param("problemId") Integer problemId,
                                   @Param("week") Long week, @Param("index") Long index) {
        roadmapProblemService.save(roadmapId, problemId, week, index);
    }

    /**
     * 특정 문제를 수정한다.
     */
    @PutMapping("/update")
    public void updateRoadmapProblem(@Param("id") Long id, @Param("roadmapId") Long roadmapId,
                                     @Param("week") Long week, @Param("index") Long index) {
        roadmapProblemService.update(id, roadmapId, week, index);
    }

    /**
     * 로드맵의 특정 문제를 삭제한다.
     */
    @DeleteMapping("/delete")
    public void deleteRoadmapProblem(@Param("id") Long id) {
        roadmapProblemService.delete(id);
    }
}
