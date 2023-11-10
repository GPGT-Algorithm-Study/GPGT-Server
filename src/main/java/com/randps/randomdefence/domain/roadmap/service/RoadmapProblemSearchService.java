package com.randps.randomdefence.domain.roadmap.service;

import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblem;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblemRepository;
import com.randps.randomdefence.domain.roadmap.dto.RoadmapProblemDto;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoadmapProblemSearchService {

    private final RoadmapProblemRepository roadmapProblemRepository;

    /**
     * 특정 로드맵의 모든 문제를 조회한다.
     */
    @Transactional
    public List<RoadmapProblemDto> searchTotalRoadmapProblems(Long roadmapId) {
        List<RoadmapProblem> roadmapProblems = roadmapProblemRepository.findAllByRoadmapIdOrderByIndexAsc(roadmapId);
        List<RoadmapProblemDto> roadmapProblemDtos = new ArrayList<>();

        for (RoadmapProblem roadmapProblem : roadmapProblems) {
            roadmapProblemDtos.add(roadmapProblem.toDto());
        }

        return roadmapProblemDtos;
    }

    /**
     * 특정 로드맵의 특정 주차의 모든 문제를 조회한다.
     */
    @Transactional
    public List<RoadmapProblemDto> searchWeeklyRoadmapProblems(Long roadmapId, Long week) {
        List<RoadmapProblem> roadmapProblems = roadmapProblemRepository.findAllByRoadmapIdAndWeekOrderByIndexAsc(roadmapId, week);
        List<RoadmapProblemDto> roadmapProblemDtos = new ArrayList<>();

        for (RoadmapProblem roadmapProblem : roadmapProblems) {
            roadmapProblemDtos.add(roadmapProblem.toDto());
        }

        return roadmapProblemDtos;
    }

}
