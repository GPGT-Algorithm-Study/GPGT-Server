package com.randps.randomdefence.domain.roadmap.controller;

import com.randps.randomdefence.domain.roadmap.dto.RoadmapSimpleDto;
import com.randps.randomdefence.domain.roadmap.service.RoadmapSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roadmap/search")
public class RoadmapSearchController {

    private final RoadmapSearchService roadmapSearchService;

//    /*
//     * 모든 로드맵을 조회한다.
//     */
//    @GetMapping("/all")
//    public List<RoadmapDto> searchAllRoadmap() {
//        return roadmapSearchService.searchAllRoadmap();
//    }

    /*
     * 모든 로드맵 조회 (Simple)
     */
    @GetMapping("/all")
    public List<RoadmapSimpleDto> searchAllRoadmaps() {
        return roadmapSearchService.searchAllRoadmapSimple();
    }

    /*
     * 특정 로드맵 조회 (Simple)
     */
    @GetMapping("/")
    public RoadmapSimpleDto searchRoadmap(@Param("roadmapId") Long roadmapId) {
        return roadmapSearchService.searchRoadmapSimple(roadmapId);
    }

    /**
     * 특정 classification에 따른 모든 로드맵 조회 (simple)
     */
    @GetMapping("/all/classification")
    public List<RoadmapSimpleDto> searchAllRoadmapSimpleByClassification(@Param("query") String query) {
        return roadmapSearchService.searchAllRoadmapByClassificationSimple(query);
    }

}
