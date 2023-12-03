package com.randps.randomdefence.domain.roadmap.service;

import com.randps.randomdefence.domain.roadmap.domain.Roadmap;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblemRepository;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapRepository;
import com.randps.randomdefence.domain.roadmap.dto.RoadmapDto;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;

    private final RoadmapProblemRepository roadmapProblemRepository;

    private final RoadmapTagService roadmapTagService;

    /**
     * 로드맵을 생성한다.
     */
    @Transactional
    public RoadmapDto save(String lectureId, String name, String classification, String description, String difficulty) {
        Roadmap roadmap = new Roadmap(lectureId, name, classification, description, difficulty, 0L);
        roadmapRepository.save(roadmap);
        return roadmap.toDto();
    }

    /**
     * 특정 로드맵을 수정한다.
     */
    @Transactional
    public RoadmapDto update(Long id, String lectureId, String name, String classification, String description, String difficulty) {
        Roadmap roadmap = roadmapRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Roadmap not found"));
        roadmap.update(lectureId, name, classification, description, difficulty);
        roadmapRepository.save(roadmap);
        return roadmap.toDto();
    }

    /**
     * 특정 로드맵을 삭제한다.
     */
    @Transactional
    public void delete(Long id) {
        roadmapTagService.deleteRoadmapTags(id);
        roadmapProblemRepository.deleteAllByRoadmapId(id);
        roadmapRepository.deleteById(id);
    }

}
