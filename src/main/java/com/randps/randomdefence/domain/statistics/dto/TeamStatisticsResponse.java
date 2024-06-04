package com.randps.randomdefence.domain.statistics.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class TeamStatisticsResponse {

    public List<TeamStatisticsDto> teams;

    @Builder
    public TeamStatisticsResponse(List<TeamStatisticsDto> teams) {
        this.teams = teams;
    }
}
