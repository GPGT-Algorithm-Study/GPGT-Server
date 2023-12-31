package com.randps.randomdefence.domain.log.infrastructure;

import com.randps.randomdefence.domain.log.domain.PointLog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLogJpaRepository extends JpaRepository<PointLog, Long> {

    Page<PointLog> findAllByOrderByCreatedDateDesc(Pageable pageable);

    List<PointLog> findAllByBojHandle(String bojHandle);

    List<PointLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable);
}
