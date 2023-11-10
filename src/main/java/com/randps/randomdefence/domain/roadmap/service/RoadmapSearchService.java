package com.randps.randomdefence.domain.roadmap.service;

import com.randps.randomdefence.domain.roadmap.domain.Roadmap;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblemRepository;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapRepository;
import com.randps.randomdefence.domain.roadmap.dto.RoadmapDto;
import com.randps.randomdefence.domain.roadmap.dto.RoadmapSimpleDto;
import com.randps.randomdefence.domain.roadmap.dto.RoadmapTagDto;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoadmapSearchService {

    private final RoadmapRepository roadmapRepository;

    private final RoadmapProblemRepository roadmapProblemRepository;

    private final RoadmapTagService roadmapTagService;

    private final RoadmapTagSearchService roadmapTagSearchService;

    /**
     * 모든 로드맵을 조회한다.
     */
    @Transactional
    public List<RoadmapDto> searchAllRoadmap() {
        List<Roadmap> roadmaps = roadmapRepository.findAll();
        List<RoadmapDto> roadmapDtos = new ArrayList<>();
        for (Roadmap roadmap : roadmaps) {
            roadmapDtos.add(roadmap.toDto());
        }
        return roadmapDtos;
    }

    /**
     * 모든 로드맵을 조회한다. (Simple)
     */
    @Transactional
    public List<RoadmapSimpleDto> searchAllRoadmapSimple() {
        List<Roadmap> roadmaps = roadmapRepository.findAll();
        List<RoadmapSimpleDto> roadmapSimpleDtos = new ArrayList<>();
        for (Roadmap roadmap : roadmaps) {
            List<RoadmapTagDto> tagDtos = roadmapTagSearchService.searchRoadmapTagsByCountDesc(roadmap.getId());
            roadmapSimpleDtos.add(new RoadmapSimpleDto(roadmap, tagDtos));
        }
        return roadmapSimpleDtos;
    }

    /**
     * 특정 로드맵을 조회한다. (Simple)
     */
    @Transactional
    public RoadmapSimpleDto searchRoadmapSimple(Long roadmapId) {
        Roadmap roadmap = roadmapRepository.findById(roadmapId).orElseThrow(() -> new IllegalArgumentException("Roadmap not found"));
        List<RoadmapTagDto> tagDtos = roadmapTagSearchService.searchRoadmapTagsByCountDesc(roadmap.getId());
        return new RoadmapSimpleDto(roadmap, tagDtos);
    }
}
