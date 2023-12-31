package com.randps.randomdefence.domain.log.mock;

import com.randps.randomdefence.domain.log.domain.PointLog;
import com.randps.randomdefence.domain.log.service.port.PointLogRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FakePointLogRepository implements PointLogRepository {

    private final List<PointLog> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public PointLog save(PointLog pointLog) {
        if (pointLog.getId() == null || pointLog.getId() == 0L) {
            autoIncreasingCount++;
            PointLog newPointLog = PointLog.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(pointLog.getBojHandle())
                    .changedValue(pointLog.getChangedValue())
                    .description(pointLog.getDescription())
                    .state(pointLog.getState())
                    .build();
            data.add(newPointLog);
            return newPointLog;
        } else {
            data.removeIf(elem -> elem.getId().equals(pointLog.getId()));
            PointLog newPointLog = PointLog.builder()
                    .id(pointLog.getId())
                    .bojHandle(pointLog.getBojHandle())
                    .changedValue(pointLog.getChangedValue())
                    .description(pointLog.getDescription())
                    .state(pointLog.getState())
                    .build();
            data.add(newPointLog);
            return newPointLog;
        }
    }

    // TODO : implement
    @Override
    public Page<PointLog> findAllByOrderByCreatedDateDesc(Pageable pageable) {
        return null;
    }

    @Override
    public List<PointLog> findAllByBojHandle(String bojHandle) {
        return null;
    }

    @Override
    public List<PointLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<PointLog> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<PointLog> findAll() {
        return null;
    }
}
