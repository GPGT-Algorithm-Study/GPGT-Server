package com.randps.randomdefence.domain.log.controller;

import com.randps.randomdefence.domain.log.domain.PointLog;
import com.randps.randomdefence.domain.log.domain.WarningLog;
import com.randps.randomdefence.domain.log.service.PointLogSaveService;
import com.randps.randomdefence.domain.log.service.PointLogSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
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
     * 모든 유저의 포인트 로그를 페이지 단위로 조회해서 반환한다.
     */
    @GetMapping("/all/page")
    public Page<PointLog> findAllPagingPointLog(Pageable pageable) {
        return pointLogSearchService.findAllPagingLog(pageable);
    }



    /*
     * 특정 유저의 포인트 로그를 조회해서 반환한다.
     */
    @GetMapping("/user")
    public List<PointLog> findUserAllPointLog(@Param("bojHandle") String bojHandle) {
        return pointLogSearchService.findAllLogByBojHandle(bojHandle);
    }

    /*
     * 특정 유저의 포인트 로그를 페이지 단위로 조회해서 반환한다.
     */
    @GetMapping("/user/page")
    public List<PointLog> findUserPagingPointLog(@Param("bojHandle") String bojHandle, Pageable pageable) {
        return pointLogSearchService.findPagingLogByBojHandle(bojHandle, pageable);
    }

    /*
     * 로그 아이디로 특정 로그를 롤백한다.
     */
    @PutMapping("/rollback")
    public PointLog rollbackByPointLogId(@Param("logId") Long logId) {
        return pointLogSaveService.rollbackPointLog(logId);
    }

    /*
     * 특정 유저에게 특정 메시지로 포인트를 부여하거나 감소시킨다. (ADMIN)
     */
    @PostMapping("/user/save")
    public PointLog saveUserPointLog(@Param("bojHandle") String bojHandle, @Param("changedValue") Integer changedValue, @Param("description") String description) {
        // 로그 메시지가 없다면 디폴트 로그 메시지를 생성한다.
        if (description.isEmpty() || description.isBlank()) {
            description = "Changed by an administrator.";
        }
        return pointLogSaveService.saveAndApplyPointLog(bojHandle, changedValue, description, true);
    }
}
