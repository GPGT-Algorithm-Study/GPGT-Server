package com.randps.randomdefence.domain.log.infrastructure;

import com.randps.randomdefence.domain.log.domain.WarningLog;
import com.randps.randomdefence.domain.log.service.port.WarningLogRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WarningLogRepositoryAdapter implements WarningLogRepository {

    private final WarningLogJpaRepository warningLogJpaRepository;

    @Override
    public Page<WarningLog> findAllByOrderByCreatedDateDesc(Pageable pageable) {
        return warningLogJpaRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    @Override
    public List<WarningLog> findAllByBojHandle(String bojHandle) {
        return warningLogJpaRepository.findAllByBojHandle(bojHandle);
    }

    @Override
    public List<WarningLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable) {
        return warningLogJpaRepository.findAllByBojHandleOrderByCreatedDateDesc(bojHandle, pageable);
    }

    @Override
    public WarningLog save(WarningLog warningLog) {
        return warningLogJpaRepository.save(warningLog);
    }

    @Override
    public Optional<WarningLog> findById(Long id) {
        return warningLogJpaRepository.findById(id);
    }

    @Override
    public List<WarningLog> findAll() {
        return warningLogJpaRepository.findAll();
    }

}
