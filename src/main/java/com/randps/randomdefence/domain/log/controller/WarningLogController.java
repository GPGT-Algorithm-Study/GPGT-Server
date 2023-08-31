package com.randps.randomdefence.domain.log.controller;

import com.randps.randomdefence.domain.log.domain.PointLog;
import com.randps.randomdefence.domain.log.domain.WarningLog;
import com.randps.randomdefence.domain.log.service.WarningLogSaveService;
import com.randps.randomdefence.domain.log.service.WarningLogSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/log/warning")
public class WarningLogController {

    private final WarningLogSearchService warningLogSearchService;

    private final WarningLogSaveService warningLogSaveService;

    /*
     * 모든 유저의 경고 로그를 조회해서 반환한다.
     */
    @GetMapping("/all")
    public List<WarningLog> findAllWarningLog() {
        return warningLogSearchService.findAllLog();
    }


    /*
     * 특정 유저의 경고 로그를 조회해서 반환한다.
     */
    @GetMapping("/user")
    public List<WarningLog> findUserAllWarningLog(@Param("bojHandle") String bojHandle) {
        return warningLogSearchService.findAllLogByBojHandle(bojHandle);
    }

    /*
     * 특정 유저의 경고 로그를 페이지 단위로 조회해서 반환한다.
     */
    @GetMapping("/user/page")
    public List<WarningLog> findUserPagingWarningLog(@Param("bojHandle") String bojHandle, Pageable pageable) {
        return warningLogSearchService.findPagingLogByBojHandle(bojHandle, pageable);
    }

    /*
     * 로그 아이디로 특정 로그를 롤백한다.
     */
    @PutMapping("/rollback")
    public WarningLog rollbackByWarningLogId(@Param("logId") Long logId) {
        return warningLogSaveService.rollbackPointLog(logId);
    }
}
