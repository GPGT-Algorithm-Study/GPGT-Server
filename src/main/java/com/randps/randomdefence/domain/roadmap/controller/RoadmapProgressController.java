package com.randps.randomdefence.domain.roadmap.controller;

import com.randps.randomdefence.domain.roadmap.dto.ProgressDto;
import com.randps.randomdefence.domain.roadmap.service.RoadmapProgressService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roadmap/progress")
public class RoadmapProgressController {

    private final RoadmapProgressService roadmapProgressService;

    @GetMapping("/user")
    public List<ProgressDto> searchAllUserRoadmapsProgress(@Param("bojHandle") String bojHandle) {
        return roadmapProgressService.getUserAllRoadmapProgress(bojHandle);
    }

}
