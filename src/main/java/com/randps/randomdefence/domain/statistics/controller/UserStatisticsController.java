package com.randps.randomdefence.domain.statistics.controller;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stat/user")
public class UserStatisticsController {

    private final UserStatisticsService userStatisticsService;

    /*
     * 유저 통계를 조회한다.
     */
    @GetMapping("")
    public UserStatistics findUserStat(@Param("bojHandle") String bojHandle) {
        UserStatistics userStatistics = userStatisticsService.findByBojHandle(bojHandle);

        return userStatistics;
    }

}
