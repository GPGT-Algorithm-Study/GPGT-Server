package com.randps.randomdefence.domain.log.infrastructure;

import com.randps.randomdefence.domain.log.domain.PointLog;
import com.randps.randomdefence.domain.log.service.port.PointLogRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointLogRepositoryAdapter implements PointLogRepository {

    private final PointLogJpaRepository pointLogJpaRepository;

    @Override
    public Page<PointLog> findAllByOrderByCreatedDateDesc(Pageable pageable) {
        return pointLogJpaRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    @Override
    public List<PointLog> findAllByBojHandle(String bojHandle) {
        return pointLogJpaRepository.findAllByBojHandle(bojHandle);
    }

    @Override
    public List<PointLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable) {
        return pointLogJpaRepository.findAllByBojHandleOrderByCreatedDateDesc(bojHandle, pageable);
    }

    @Override
    public PointLog save(PointLog pointLog) {
        return pointLogJpaRepository.save(pointLog);
    }

    @Override
    public Optional<PointLog> findById(Long id) {
        return pointLogJpaRepository.findById(id);
    }

    @Override
    public List<PointLog> findAll() {
        return pointLogJpaRepository.findAll();
    }
}
