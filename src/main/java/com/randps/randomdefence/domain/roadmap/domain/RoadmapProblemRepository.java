package com.randps.randomdefence.domain.roadmap.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapProblemRepository extends JpaRepository<RoadmapProblem, Long> {

    Optional<RoadmapProblem> findByProblemId(Integer problemId);

    Optional<RoadmapProblem> findByRoadmapIdAndProblemId(Long roadmapId, Integer problemId);
    List<RoadmapProblem> findAllByRoadmapId(Long roadmapId);

    void deleteAllByRoadmapId(Long roadmapId);

    List<RoadmapProblem> findAllByRoadmapIdOrderByIdxAsc(Long roadmapId);
    List<RoadmapProblem> findAllByRoadmapIdAndWeekOrderByIdxAsc(Long roadmapId, Long week);
}
