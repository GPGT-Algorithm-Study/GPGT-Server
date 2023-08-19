package com.randps.randomdefence.domain.statistics.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {

    Optional<UserStatistics> findByBojHandle(String bojHandle);


}
