package com.randps.randomdefence.domain.log.service;

import com.randps.randomdefence.domain.log.domain.PointLog;
import com.randps.randomdefence.domain.log.service.port.PointLogRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointLogSearchService {

    private final PointLogRepository pointLogRepository;

    /*
     * 서버의 모든 포인트 로그를 조회한다.
     */
    @Transactional
    public List<PointLog> findAllLog() {
        List<PointLog> pointLogs =  pointLogRepository.findAll();
        if (pointLogs == null)
            pointLogs = new ArrayList<>();

        return pointLogs;
    }

    /*
     * 서버의 모든 포인트 로그를 페이지 단위로 조회한다.
     */
    @Transactional
    public Page<PointLog> findAllPagingLog(Pageable pageable) {
        Page<PointLog> pointLogs =  pointLogRepository.findAllByOrderByCreatedDateDesc(pageable);

        return pointLogs;
    }

    /*
     * 특정 유저의 모든 포인트 로그를 조회한다.
     */
    @Transactional
    public List<PointLog> findAllLogByBojHandle(String bojHandle) {
        List<PointLog> pointLogs = pointLogRepository.findAllByBojHandle(bojHandle);
        if (pointLogs == null)
            pointLogs = new ArrayList<>();
        return pointLogs;
    }

    /*
     * 특정 유저의 모든 포인트 로그를 페이지 단위로 조회한다.
     */
    @Transactional
    public List<PointLog> findPagingLogByBojHandle(String bojHandle, Pageable pageable) {
        List<PointLog> pointLogs = pointLogRepository.findAllByBojHandleOrderByCreatedDateDesc(bojHandle, pageable);
        if (pointLogs == null)
            pointLogs = new ArrayList<>();
        return pointLogs;
    }
}
