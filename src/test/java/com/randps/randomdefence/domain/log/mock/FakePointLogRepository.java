package com.randps.randomdefence.domain.log.mock;

import com.randps.randomdefence.domain.log.domain.PointLog;
import com.randps.randomdefence.domain.log.service.port.PointLogRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakePointLogRepository implements PointLogRepository {

    private final List<PointLog> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public PointLog save(PointLog pointLog) {
        if (pointLog.getId() == null || pointLog.getId() == 0L) {
            autoIncreasingCount++;
            PointLog newPointLog = PointLog.builder().id(autoIncreasingCount).bojHandle(pointLog.getBojHandle())
                    .changedValue(pointLog.getChangedValue()).description(pointLog.getDescription())
                    .state(pointLog.getState()).build();
            data.add(newPointLog);
            return newPointLog;
        } else {
            data.removeIf(elem -> elem.getId().equals(pointLog.getId()));
            PointLog newPointLog = PointLog.builder().id(pointLog.getId()).bojHandle(pointLog.getBojHandle())
                    .changedValue(pointLog.getChangedValue()).description(pointLog.getDescription())
                    .state(pointLog.getState()).build();
            data.add(newPointLog);
            return newPointLog;
        }
    }

    @Override
    public Page<PointLog> findAllByOrderByCreatedDateDesc(Pageable pageable) {
        List<PointLog> pointLogs;
        long total = data.size();

        if (pageable.getPageNumber() == 0) {
            pointLogs = data.stream().sorted(Comparator.comparing(PointLog::getCreatedDate).reversed())
                    .limit(pageable.getPageSize()).collect(Collectors.toList());
        } else {
            pointLogs = data.stream().sorted(Comparator.comparing(PointLog::getCreatedDate).reversed())
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber()).limit(pageable.getPageSize())
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(pointLogs, pageable, total);
    }

    @Override
    public List<PointLog> findAllByBojHandle(String bojHandle) {
        return data.stream().filter(elem -> elem.getBojHandle().equals(bojHandle)).collect(Collectors.toList());
    }

    @Override
    public List<PointLog> findAllByBojHandleOrderByCreatedDateDesc(String bojHandle, Pageable pageable) {
        List<PointLog> pointLogs;

        if (pageable.getPageNumber() == 0) {
            pointLogs = data.stream().filter(elem -> elem.getBojHandle().equals(bojHandle))
                    .sorted(Comparator.comparing(PointLog::getCreatedDate).reversed()).limit(pageable.getPageSize())
                    .collect(Collectors.toList());
        } else {
            pointLogs = data.stream().filter(elem -> elem.getBojHandle().equals(bojHandle))
                    .sorted(Comparator.comparing(PointLog::getCreatedDate).reversed())
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber()).limit(pageable.getPageSize())
                    .collect(Collectors.toList());
        }
        return pointLogs;
    }

    @Override
    public Optional<PointLog> findById(Long id) {
        return data.stream().filter(elem -> elem.getId().equals(id)).findAny();
    }

    @Override
    public List<PointLog> findAll() {
        return data;
    }

    @Override
    public void deleteAllByBojHandle(String bojHandle) {
        data.removeIf(elem -> elem.getBojHandle().equals(bojHandle));
    }
}
