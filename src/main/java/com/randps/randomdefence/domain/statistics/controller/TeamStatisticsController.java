package com.randps.randomdefence.domain.statistics.controller;

import com.randps.randomdefence.domain.statistics.dto.TeamStatisticsResponse;
import com.randps.randomdefence.domain.statistics.service.TeamStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stat/team")
public class TeamStatisticsController {

    private final TeamStatisticsService teamStatisticsService;

    /**
     * 전체 팀 통계를 조회한다. (Querydsl)
     */
    @GetMapping("/all")
    public TeamStatisticsResponse findAllTeamStat() {
        TeamStatisticsResponse teamStatisticsResponse = teamStatisticsService.findAllTeamStat();

        return teamStatisticsResponse;
    }
}
