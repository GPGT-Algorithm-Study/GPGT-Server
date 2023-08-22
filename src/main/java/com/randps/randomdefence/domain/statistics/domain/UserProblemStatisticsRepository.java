package com.randps.randomdefence.domain.statistics.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProblemStatisticsRepository extends JpaRepository<UserProblemStatistics, Long> {
    Optional<UserProblemStatistics> findByBojHandle(String bojHandle);
}
