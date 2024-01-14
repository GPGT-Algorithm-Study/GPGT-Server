package com.randps.randomdefence.domain.log.infrastructure;

import com.randps.randomdefence.domain.log.domain.WarningLog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarningLogJpaRepository extends JpaRepository<WarningLog, Long> {
    Page<WarningLog> findAllByOrderByCreatedDateDesc(Pageable pageable);

    List<WarningLog> findAllByBojHandle(String bojHandle);

    List<WarningLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable);

    void deleteAllByBojHandle(String bojHandle);
}
