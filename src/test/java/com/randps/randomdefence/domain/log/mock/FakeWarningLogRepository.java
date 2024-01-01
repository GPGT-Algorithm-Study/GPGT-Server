package com.randps.randomdefence.domain.log.mock;

import com.randps.randomdefence.domain.log.domain.WarningLog;
import com.randps.randomdefence.domain.log.service.port.WarningLogRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakeWarningLogRepository implements WarningLogRepository {

    private final List<WarningLog> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public WarningLog save(WarningLog warningLog) {
        if (warningLog.getId() == null || warningLog.getId() == 0L) {
            autoIncreasingCount++;
            WarningLog newWarningLog = WarningLog.builder().id(autoIncreasingCount).bojHandle(warningLog.getBojHandle())
                    .changedValue(warningLog.getChangedValue()).description(warningLog.getDescription())
                    .state(warningLog.getState()).build();
            data.add(newWarningLog);
            return newWarningLog;
        } else {
            data.removeIf(elem -> elem.getId().equals(warningLog.getId()));
            WarningLog newWarningLog = WarningLog.builder().id(autoIncreasingCount).bojHandle(warningLog.getBojHandle())
                    .changedValue(warningLog.getChangedValue()).description(warningLog.getDescription())
                    .state(warningLog.getState()).build();
            data.add(newWarningLog);
            return newWarningLog;
        }
    }

    @Override
    public Page<WarningLog> findAllByOrderByCreatedDateDesc(Pageable pageable) {
        List<WarningLog> warningLogs;
        long total = data.size();

        if (pageable.getPageNumber() == 0) {
            warningLogs = data.stream().sorted(Comparator.comparing(WarningLog::getCreatedDate).reversed())
                    .limit(pageable.getPageSize()).collect(Collectors.toList());
        } else {
            warningLogs = data.stream().sorted(Comparator.comparing(WarningLog::getCreatedDate).reversed())
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber()).limit(pageable.getPageSize())
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(warningLogs, pageable, total);
    }

    @Override
    public List<WarningLog> findAllByBojHandle(String bojHandle) {
        return data.stream().filter(elem -> elem.getBojHandle().equals(bojHandle)).collect(Collectors.toList());
    }

    @Override
    public List<WarningLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable) {
        List<WarningLog> warningLogs;

        if (pageable.getPageNumber() == 0) {
            warningLogs = data.stream().filter(elem -> elem.getBojHandle().equals(bojHandle))
                    .sorted(Comparator.comparing(WarningLog::getCreatedDate).reversed()).limit(pageable.getPageSize())
                    .collect(Collectors.toList());
        } else {
            warningLogs = data.stream().filter(elem -> elem.getBojHandle().equals(bojHandle))
                    .sorted(Comparator.comparing(WarningLog::getCreatedDate).reversed())
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber()).limit(pageable.getPageSize())
                    .collect(Collectors.toList());
        }
        return warningLogs;
    }

    @Override
    public Optional<WarningLog> findById(Long id) {
        return data.stream().filter(elem -> elem.getId().equals(id)).findAny();
    }

    @Override
    public List<WarningLog> findAll() {
        return data;
    }
}
