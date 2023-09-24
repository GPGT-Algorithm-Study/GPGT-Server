package com.randps.randomdefence.domain.log.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointLogRepository extends JpaRepository<PointLog, Long> {

    Page<PointLog> findAllByOrderByCreatedDateDesc(Pageable pageable);

    List<PointLog> findAllByBojHandle(String bojHandle);

    List<PointLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable);
}
