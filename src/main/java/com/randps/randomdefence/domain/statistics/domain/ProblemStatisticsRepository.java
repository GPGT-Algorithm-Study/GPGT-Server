package com.randps.randomdefence.domain.statistics.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemStatisticsRepository extends JpaRepository<ProblemStatistics, Long>, ProblemStatisticsRepositoryCustom {
    Optional<ProblemStatistics> findByProblemId(Integer problemId);

    List<ProblemStatistics> findTop50ByOrderBySolvedCountDesc();
}
