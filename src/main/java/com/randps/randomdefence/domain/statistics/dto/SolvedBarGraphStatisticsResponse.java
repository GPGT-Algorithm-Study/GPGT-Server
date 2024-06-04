package com.randps.randomdefence.domain.statistics.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class SolvedBarGraphStatisticsResponse {
    private List<SolvedBarDto> userBars;

    @Builder
    public SolvedBarGraphStatisticsResponse(List<SolvedBarDto> userBars) {
        this.userBars = userBars;
    }

    public SolvedBarGraphStatisticsResponse() {
        this.userBars = null;
    }
}
