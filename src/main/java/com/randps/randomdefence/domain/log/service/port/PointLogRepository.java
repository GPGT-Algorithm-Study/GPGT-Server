package com.randps.randomdefence.domain.log.service.port;

import com.randps.randomdefence.domain.log.domain.PointLog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointLogRepository {
    Page<PointLog> findAllByOrderByCreatedDateDesc(Pageable pageable);

    List<PointLog> findAllByBojHandle(String bojHandle);

    List<PointLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable);

    PointLog save(PointLog pointLog);

    Optional<PointLog> findById(Long id);

    List<PointLog> findAll();
}
