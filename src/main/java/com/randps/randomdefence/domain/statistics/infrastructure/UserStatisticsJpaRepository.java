package com.randps.randomdefence.domain.statistics.infrastructure;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatisticsJpaRepository extends JpaRepository<UserStatistics, Long> {

    Optional<UserStatistics> findByBojHandle(String bojHandle);

}
