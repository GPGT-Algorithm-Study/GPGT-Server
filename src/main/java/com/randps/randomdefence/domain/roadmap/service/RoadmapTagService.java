package com.randps.randomdefence.domain.roadmap.service;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.roadmap.domain.Roadmap;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblem;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblemRepository;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapRepository;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapTag;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapTagRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoadmapTagService {

    private final RoadmapRepository roadmapRepository;

    private final ProblemService problemService;

    private final RoadmapProblemRepository roadmapProblemRepository;

    private final RoadmapTagRepository roadmapTagRepository;

    //TODO: 로드맵 삭제시에 로드맵 태그도 다 삭제 해줘야 함.

    /**
     * 모든 로드맵의 태그를 적용한다.
     */
    @Transactional
    public void calculateAllRoadmapTags() {
        List<Roadmap> roadmaps = roadmapRepository.findAll();

        for (Roadmap roadmap : roadmaps) {
            calculateRoadmapTags(roadmap.getId());
        }
    }

    /**
     * 특정 로드맵의 모든 문제에 따른 태그를 적용한다.
     */
    @Transactional
    public void calculateRoadmapTags(Long roadmapId) {
        // 초기화
        deleteRoadmapTags(roadmapId);

        List<RoadmapProblem> roadmapProblems = roadmapProblemRepository.findAllByRoadmapId(roadmapId);
        for (RoadmapProblem problem : roadmapProblems) {
            applyProblemTagToRoadmap(roadmapId, problem.getProblemId());
        }
    }

    /**
     * 특정 로드맵의 특정 문제의 태그를 로드맵 태그에 반영한다.
     */
    @Transactional
    public void applyProblemTagToRoadmap(Long roadmapId, Integer problemId) {
        ProblemDto problem = problemService.findProblem(problemId);
        roadmapRepository.findById(roadmapId).orElseThrow(() -> new IllegalArgumentException("Roadmap with id " + roadmapId + " does not exist"));
        List<String> tags = problem.getTags();

        for (String tag : tags) {
            incrementTagCount(roadmapId, tag);
        }
    }

    /**
     * 특정 로그맵의 특정 문제의 태그를 로드맵 태그에서 제거한다.
     */
    @Transactional
    public void deleteProblemTagFromRoadmap(Long roadmapId, Integer problemId) {
        ProblemDto problem = problemService.findProblem(problemId);
        roadmapRepository.findById(roadmapId).orElseThrow(() -> new IllegalArgumentException("Roadmap with id " + roadmapId + " does not exist"));
        List<String> tags = problem.getTags();

        for (String tag : tags) {
            decrementTagCount(roadmapId, tag);
        }
    }

    /**
     * 특정 태그의 개수를 1 증가시킨다.
     */
    public void incrementTagCount(Long roadmapId, String name) {
        Optional<RoadmapTag> tag = roadmapTagRepository.findByRoadmapIdAndName(roadmapId, name);

        if(tag.isEmpty()) {
            RoadmapTag newTag = new RoadmapTag(roadmapId, name);
            newTag.incrementCount();
            roadmapTagRepository.save(newTag);
            return;
        }
        RoadmapTag oldTag = tag.get();
        oldTag.incrementCount();
        roadmapTagRepository.save(oldTag);
    }

    /**
     * 특정 태그의 개수를 1 감소시킨다.
     */
    public void decrementTagCount(Long roadmapId, String name) {
        Optional<RoadmapTag> tag = roadmapTagRepository.findByRoadmapIdAndName(roadmapId, name);

        if (tag.isEmpty()) {
            return ;
        }
        RoadmapTag oldTag = tag.get();
        if (oldTag.decrementCount()) {
            roadmapTagRepository.delete(oldTag);
            return ;
        }
        roadmapTagRepository.save(oldTag);
    }

    /**
     * 특정 로드맵의 모든 태그를 삭제한다.
     */
    @Transactional
    public void deleteRoadmapTags(Long roadmapId) {
        roadmapTagRepository.deleteAllByRoadmapId(roadmapId);
    }

}
