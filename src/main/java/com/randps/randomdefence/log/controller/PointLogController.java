package com.randps.randomdefence.log.controller;

import com.randps.randomdefence.log.domain.PointLog;
import com.randps.randomdefence.log.service.PointLogSaveService;
import com.randps.randomdefence.log.service.PointLogSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/log/point")
public class PointLogController {

    private final PointLogSearchService pointLogSearchService;

    private final PointLogSaveService pointLogSaveService;

    /*
     * 모든 유저의 포인트 로그를 조회해서 반환한다.
     */
    @GetMapping("/all")
    public List<PointLog> findAllPointLog() {
        return pointLogSearchService.findAllLog();
    }


    /*
     * 특정 유저의 포인트 로그를 조회해서 반환한다.
     */
    @GetMapping("/user")
    public List<PointLog> findUserAllPointLog(@Param("bojHandle") String bojHandle) {
        return pointLogSearchService.findAllLogByBojHandle(bojHandle);
    }

    /*
     * 로그 아이디로 특정 로그를 롤백한다.
     */
    @PutMapping("/rollback")
    public PointLog rollbackByPointLogId(@Param("logId") Long logId) {
        return pointLogSaveService.rollbackPointLog(logId);
    }
}
