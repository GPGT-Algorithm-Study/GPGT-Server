package com.randps.randomdefence.domain.roadmap.service;

import com.randps.randomdefence.domain.roadmap.domain.Roadmap;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblem;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapProblemRepository;
import com.randps.randomdefence.domain.roadmap.domain.RoadmapRepository;
import com.randps.randomdefence.domain.roadmap.dto.ProgressDto;
import com.randps.randomdefence.domain.user.service.UserSolvedJudgeService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoadmapProgressService {

    private final RoadmapRepository roadmapRepository;

    private final RoadmapProblemRepository roadmapProblemRepository;

    private final UserSolvedJudgeService userSolvedJudgeService;

    /*
     * 특정 유저의 모든 로드맵의 진행도를 반환한다.
     * 0% 이상의 로드맵만 조회
     */
    @Transactional
    public List<ProgressDto> getUserAllRoadmapProgress(String bojHandle) {
        List<ProgressDto> progressDtos = new ArrayList<>();
        List<Roadmap> roadmaps = roadmapRepository.findAll();

        for (Roadmap roadmap : roadmaps) {
            Double progress = getUserRoadmapProgress(bojHandle, roadmap.getId());
            if (progress == 0) continue;
            progressDtos.add(ProgressDto.builder()
                    .roadmapId(roadmap.getId())
                    .name(roadmap.getName())
                    .progress(progress)
                    .build());
        }
        return progressDtos;
    }

    /*
     * 특정 유저의 특정 로드맵의 진행도를 반환한다.
     */
    @Transactional
    public Double getUserRoadmapProgress(String bojHandle, Long roadmapId) {
        Roadmap roadmap = roadmapRepository.findById(roadmapId).orElseThrow(() -> new IllegalArgumentException("Roadmap not found"));

        System.out.println("count : " + roadmap.getTotalProblemCount());
        if (roadmap.getTotalProblemCount() <= 0) {
            return 0D;
        }
        return (Double)((long)(((double)getUserSolvedProblemInRoadmap(bojHandle, roadmapId) / (double)roadmap.getTotalProblemCount()) * 10000) / 100D);
    }

    /*
     * 특정 유저가 로드맵에서 푼 문제 수를 반환한다.
     */
    @Transactional
    public Long getUserSolvedProblemInRoadmap(String bojHandle, Long roadmapId) {
        List<RoadmapProblem> roadmapProblems = roadmapProblemRepository.findAllByRoadmapId(roadmapId);
        Long count = 0L;

        for (RoadmapProblem problem : roadmapProblems) {
            if (userSolvedJudgeService.isSolved(bojHandle, problem.getProblemId())) {
                count++;
            }
        }
        return count;
    }

}
