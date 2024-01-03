package com.randps.randomdefence.domain.log.service.port;

import com.randps.randomdefence.domain.log.domain.WarningLog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WarningLogRepository {
    Page<WarningLog> findAllByOrderByCreatedDateDesc(Pageable pageable);

    List<WarningLog> findAllByBojHandle(String bojHandle);

    List<WarningLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable);

    WarningLog save(WarningLog warningLog);

    Optional<WarningLog> findById(Long id);

    List<WarningLog> findAll();
}
