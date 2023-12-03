package com.randps.randomdefence.domain.roadmap.service;

import com.randps.randomdefence.domain.roadmap.domain.Roadmap;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblem;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblemRepository;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoadmapProblemService {

    private final RoadmapRepository roadmapRepository;

    private final RoadmapProblemRepository roadmapProblemRepository;

    private final RoadmapTagService roadmapTagService;

    /**
     * 특정 로드맵의 특정 주차에 문제를 추가한다.
     */
    @Transactional
    public void save(Long roadmapId, Integer problemId, Long week, Long index) {
        Roadmap roadmap = roadmapRepository.findById(roadmapId).orElseThrow(() -> new IllegalArgumentException("roadmap not found"));
        Optional<RoadmapProblem> dupProblem = roadmapProblemRepository.findByRoadmapIdAndProblemId(roadmapId, problemId);
        if (dupProblem.isPresent()) {
            throw new IllegalArgumentException("duplicate problem found: " + dupProblem.get());
        }
        RoadmapProblem roadmapProblem = new RoadmapProblem(roadmapId, problemId, week, index);
        roadmapTagService.applyProblemTagToRoadmap(roadmapId, problemId);
        roadmap.increaseTotalProblemCount();

        roadmapRepository.save(roadmap);
        roadmapProblemRepository.save(roadmapProblem);
    }

    /**
     * 특정 문제를 수정한다.
     */
    @Transactional
    public void update(Long id, Long roadmapId, Long week, Long index) {
        roadmapRepository.findById(roadmapId).orElseThrow(() -> new IllegalArgumentException("roadmap not found"));
        RoadmapProblem roadmapProblem = roadmapProblemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("roadmap problem not found"));

        roadmapProblem.update(roadmapId, roadmapProblem.getProblemId(), week, index);

        roadmapProblemRepository.save(roadmapProblem);
    }

    /**
     * 로드맵의 특정 문제를 삭제한다.
     */
    @Transactional
    public void delete(Long id) {
        RoadmapProblem roadmapProblem = roadmapProblemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("roadmap problem not found"));
        Roadmap roadmap = roadmapRepository.findById(roadmapProblem.getRoadmapId()).orElseThrow(() -> new IllegalArgumentException("roadmap not found"));
        roadmap.decreaseTotalProblemCount();
        roadmapTagService.deleteProblemTagFromRoadmap(roadmapProblem.getRoadmapId(), roadmapProblem.getProblemId());

        roadmapRepository.save(roadmap);
        roadmapProblemRepository.delete(roadmapProblem);
    }

}
