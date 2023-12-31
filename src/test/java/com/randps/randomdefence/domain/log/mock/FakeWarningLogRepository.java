package com.randps.randomdefence.domain.log.mock;

import com.randps.randomdefence.domain.log.domain.WarningLog;
import com.randps.randomdefence.domain.log.service.port.WarningLogRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FakeWarningLogRepository implements WarningLogRepository {

    private final List<WarningLog> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public WarningLog save(WarningLog warningLog) {
        if (warningLog.getId() == null || warningLog.getId() == 0L) {
            autoIncreasingCount++;
            WarningLog newWarningLog = WarningLog.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(warningLog.getBojHandle())
                    .changedValue(warningLog.getChangedValue())
                    .description(warningLog.getDescription())
                    .state(warningLog.getState())
                    .build();
            data.add(newWarningLog);
            return newWarningLog;
        } else {
            data.removeIf(elem -> elem.getId().equals(warningLog.getId()));
            WarningLog newWarningLog = WarningLog.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(warningLog.getBojHandle())
                    .changedValue(warningLog.getChangedValue())
                    .description(warningLog.getDescription())
                    .state(warningLog.getState())
                    .build();
            data.add(newWarningLog);
            return newWarningLog;
        }
    }

    // TODO : implement
    @Override
    public Page<WarningLog> findAllByOrderByCreatedDateDesc(Pageable pageable) {
        return null;
    }

    @Override
    public List<WarningLog> findAllByBojHandle(String bojHandle) {
        return null;
    }

    @Override
    public List<WarningLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<WarningLog> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<WarningLog> findAll() {
        return null;
    }
}
