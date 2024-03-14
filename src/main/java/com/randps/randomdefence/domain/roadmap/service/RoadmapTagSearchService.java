package com.randps.randomdefence.domain.roadmap.service;

import com.randps.randomdefence.domain.problem.service.port.ProblemRepository;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblemRepository;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapRepository;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapTag;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapTagRepository;
import com.randps.randomdefence.domain.roadmap.dto.RoadmapTagDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoadmapTagSearchService {

    private final RoadmapRepository roadmapRepository;

    private final ProblemRepository problemRepository;

    private final RoadmapProblemRepository roadmapProblemRepository;

    private final RoadmapTagRepository roadmapTagRepository;

    /**
     * 특정 로드맵의 모든 태그를 카운트가 높은 순으로 조회한다.
     */
    public List<RoadmapTagDto> searchRoadmapTagsByCountDesc(Long roadmapId) {
        List<RoadmapTag> tags = roadmapTagRepository.findAllByRoadmapIdOrderByCountDesc(roadmapId);
        List<RoadmapTagDto> tagDtos = new ArrayList<>();

        for (RoadmapTag tag : tags) {
            tagDtos.add(tag.toDto());
        }

        return tagDtos;
    }

}
