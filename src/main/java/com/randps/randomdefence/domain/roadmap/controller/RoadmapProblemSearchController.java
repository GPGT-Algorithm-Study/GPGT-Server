package com.randps.randomdefence.domain.roadmap.controller;

import com.randps.randomdefence.domain.roadmap.dto.RoadmapProblemDto;
import com.randps.randomdefence.domain.roadmap.service.RoadmapProblemSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roadmap/problem/search")
public class RoadmapProblemSearchController {

    private final RoadmapProblemSearchService roadmapProblemSearchService;

    /**
     * 특정 로드맵의 모든 문제를 조회한다.
     */
    @GetMapping("/all")
    public List<RoadmapProblemDto> searchTotalRoadmapProblems(@Param("roadmapId") Long roadmapId) {
        return roadmapProblemSearchService.searchTotalRoadmapProblems(roadmapId);
    }

    /**
     * 특정 로드맵의 특정 주차의 모든 문제를 조회한다.
     */
    @GetMapping("/weekly")
    public List<RoadmapProblemDto> searchWeeklyRoadmapProblems(@Param("roadmapId") Long roadmapId, @Param("week") Long week) {
        return roadmapProblemSearchService.searchWeeklyRoadmapProblems(roadmapId, week);
    }

}
