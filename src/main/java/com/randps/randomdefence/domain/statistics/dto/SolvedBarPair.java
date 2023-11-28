package com.randps.randomdefence.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolvedBarPair {
    // User
    private String notionId;

    private String emoji;

    // UserProblemStatistics
    private Integer dailySolvedCountBronze;

    private Integer dailySolvedCountSilver;

    private Integer dailySolvedCountGold;

    private Integer dailySolvedCountPlatinum;

    private Integer dailySolvedCountDiamond;

    private Integer dailySolvedCountRuby;

    private Integer weeklySolvedCountBronze;

    private Integer weeklySolvedCountSilver;

    private Integer weeklySolvedCountGold;

    private Integer weeklySolvedCountPlatinum;

    private Integer weeklySolvedCountDiamond;

    private Integer weeklySolvedCountRuby;

    private Integer totalSolvedCountBronze;

    private Integer totalSolvedCountSilver;

    private Integer totalSolvedCountGold;

    private Integer totalSolvedCountPlatinum;

    private Integer totalSolvedCountDiamond;

    private Integer totalSolvedCountRuby;
}
