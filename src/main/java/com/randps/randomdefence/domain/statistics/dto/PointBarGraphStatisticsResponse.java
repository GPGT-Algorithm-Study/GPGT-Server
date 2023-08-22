package com.randps.randomdefence.domain.statistics.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PointBarGraphStatisticsResponse {

    List<PointBarDto> userBars;

    @Builder
    public PointBarGraphStatisticsResponse(List<PointBarDto> userBars) {
        this.userBars = userBars;
    }

    public PointBarGraphStatisticsResponse() {
        this.userBars = null;
    }
}
