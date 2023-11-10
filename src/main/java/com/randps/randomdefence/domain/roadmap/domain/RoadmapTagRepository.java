package com.randps.randomdefence.domain.roadmap.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapTagRepository extends JpaRepository<RoadmapTag, Long> {

    List<RoadmapTag> findAllByRoadmapId(Long roadmapId);

    List<RoadmapTag> findAllByRoadmapIdOrderByCountDesc(Long roadmapId);

    Optional<RoadmapTag> findByRoadmapIdAndName(Long roadmapId, String name);

    void deleteAllByRoadmapId(Long roadmapId);
}
