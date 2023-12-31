package com.randps.randomdefence.domain.statistics.infrastructure;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProblemStatisticsJpaRepository extends JpaRepository<UserProblemStatistics, Long> {
    Optional<UserProblemStatistics> findByBojHandle(String bojHandle);
}
