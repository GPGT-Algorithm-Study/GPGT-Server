package com.randps.randomdefence.domain.roadmap.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapProblemRepository extends JpaRepository<RoadmapProblem, Long> {

    Optional<RoadmapProblem> findByProblemId(Integer problemId);
    List<RoadmapProblem> findAllByRoadmapId(Long roadmapId);

    void deleteAllByRoadmapId(Long roadmapId);

    List<RoadmapProblem> findAllByRoadmapIdOrderByIndexAsc(Long roadmapId);
    List<RoadmapProblem> findAllByRoadmapIdAndWeekOrderByIndexAsc(Long roadmapId, Long week);
}
