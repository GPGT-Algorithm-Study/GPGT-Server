package com.randps.randomdefence.domain.log.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointLogRepository extends JpaRepository<PointLog, Long> {
    List<PointLog> findAllByBojHandle(String bojHandle);

}
