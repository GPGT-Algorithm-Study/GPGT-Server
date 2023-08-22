package com.randps.randomdefence.domain.statistics.controller;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.dto.PointBarGraphStatisticsResponse;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarGraphStatisticsResponse;
import com.randps.randomdefence.domain.statistics.service.PointBarGraphStatisticsService;
import com.randps.randomdefence.domain.statistics.service.SolvedBarGraphStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stat/graph")
public class GraphStatisticsController {

    private final SolvedBarGraphStatisticsService solvedBarGraphStatisticsService;

    private final PointBarGraphStatisticsService pointBarGraphStatisticsService;

    /*
     * 모든 유저의 난이도별 문제수 바 그래프 통계를 조회한다.
     */
    @GetMapping("/solved")
    public SolvedBarGraphStatisticsResponse findSolvedBarGraphStat() {
        SolvedBarGraphStatisticsResponse response = solvedBarGraphStatisticsService.getAllSolvedBarStatistics();

        return response;
    }

    /*
     * 모든 유저의 난이도별 문제수 바 그래프 통계를 조회한다.
     */
    @GetMapping("/point")
    public PointBarGraphStatisticsResponse findPointBarGraphStat() {
        PointBarGraphStatisticsResponse response = pointBarGraphStatisticsService.getAllPointBarStatistics();

        return response;
    }
}
