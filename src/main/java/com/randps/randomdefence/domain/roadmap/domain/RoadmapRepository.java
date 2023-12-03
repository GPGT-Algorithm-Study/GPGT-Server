package com.randps.randomdefence.domain.roadmap.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {

    List<Roadmap> findAllByClassificationContaining(String classification);

}
