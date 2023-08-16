package com.randps.randomdefence.log.service;

import com.randps.randomdefence.log.domain.PointLog;
import com.randps.randomdefence.log.domain.PointLogRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * 특정 유저의 모든 포인트 로그를 조회한다.
     */
    @Transactional
    public List<PointLog> findAllLogByBojHandle(String bojHandle) {
        List<PointLog> pointLogs = pointLogRepository.findAllByBojHandle(bojHandle);
        if (pointLogs == null)
            pointLogs = new ArrayList<>();
        return pointLogs;
    }
}
