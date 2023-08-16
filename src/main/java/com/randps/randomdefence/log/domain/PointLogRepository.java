package com.randps.randomdefence.log.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointLogRepository extends JpaRepository<PointLog, Long> {
    List<PointLog> findAllByBojHandle(String bojHandle);

}
