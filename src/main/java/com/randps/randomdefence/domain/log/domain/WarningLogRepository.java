package com.randps.randomdefence.domain.log.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarningLogRepository extends JpaRepository<WarningLog, Long> {
    Page<WarningLog> findAllByOrderByCreatedDateDesc(Pageable pageable);

    List<WarningLog> findAllByBojHandle(String bojHandle);

    List<WarningLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable);
}
