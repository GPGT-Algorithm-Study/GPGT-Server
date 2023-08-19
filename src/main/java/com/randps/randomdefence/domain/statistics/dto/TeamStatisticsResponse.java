package com.randps.randomdefence.domain.statistics.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class TeamStatisticsResponse {

    public List<TeamStatisticsDto> teams;

    @Builder
    public TeamStatisticsResponse(List<TeamStatisticsDto> teams) {
        this.teams = teams;
    }
}
