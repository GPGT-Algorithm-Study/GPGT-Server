package com.randps.randomdefence.domain.statistics.dto;

import com.randps.randomdefence.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
