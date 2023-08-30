package com.randps.randomdefence.domain.statistics.domain;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "RD_STATISTICS_USER_PROBLEM")
@Entity
public class UserProblemStatistics extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

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

    @Builder
    public UserProblemStatistics(String bojHandle, Integer dailySolvedCountBronze, Integer dailySolvedCountSilver, Integer dailySolvedCountGold, Integer dailySolvedCountPlatinum, Integer dailySolvedCountDiamond, Integer dailySolvedCountRuby, Integer weeklySolvedCountBronze, Integer weeklySolvedCountSilver, Integer weeklySolvedCountGold, Integer weeklySolvedCountPlatinum, Integer weeklySolvedCountDiamond, Integer weeklySolvedCountRuby, Integer totalSolvedCountBronze, Integer totalSolvedCountSilver, Integer totalSolvedCountGold, Integer totalSolvedCountPlatinum, Integer totalSolvedCountDiamond, Integer totalSolvedCountRuby) {
        this.bojHandle = bojHandle;
        this.dailySolvedCountBronze = dailySolvedCountBronze;
        this.dailySolvedCountSilver = dailySolvedCountSilver;
        this.dailySolvedCountGold = dailySolvedCountGold;
        this.dailySolvedCountPlatinum = dailySolvedCountPlatinum;
        this.dailySolvedCountDiamond = dailySolvedCountDiamond;
        this.dailySolvedCountRuby = dailySolvedCountRuby;
        this.weeklySolvedCountBronze = weeklySolvedCountBronze;
        this.weeklySolvedCountSilver = weeklySolvedCountSilver;
        this.weeklySolvedCountGold = weeklySolvedCountGold;
        this.weeklySolvedCountPlatinum = weeklySolvedCountPlatinum;
        this.weeklySolvedCountDiamond = weeklySolvedCountDiamond;
        this.weeklySolvedCountRuby = weeklySolvedCountRuby;
        this.totalSolvedCountBronze = totalSolvedCountBronze;
        this.totalSolvedCountSilver = totalSolvedCountSilver;
        this.totalSolvedCountGold = totalSolvedCountGold;
        this.totalSolvedCountPlatinum = totalSolvedCountPlatinum;
        this.totalSolvedCountDiamond = totalSolvedCountDiamond;
        this.totalSolvedCountRuby = totalSolvedCountRuby;
    }

    public UserProblemStatistics(String bojHandle) {
        this.bojHandle = bojHandle;
        this.dailySolvedCountBronze = 0;
        this.dailySolvedCountSilver = 0;
        this.dailySolvedCountGold = 0;
        this.dailySolvedCountPlatinum = 0;
        this.dailySolvedCountDiamond = 0;
        this.dailySolvedCountRuby = 0;
        this.weeklySolvedCountBronze = 0;
        this.weeklySolvedCountSilver = 0;
        this.weeklySolvedCountGold = 0;
        this.weeklySolvedCountPlatinum = 0;
        this.weeklySolvedCountDiamond = 0;
        this.weeklySolvedCountRuby = 0;
        this.totalSolvedCountBronze = 0;
        this.totalSolvedCountSilver = 0;
        this.totalSolvedCountGold = 0;
        this.totalSolvedCountPlatinum = 0;
        this.totalSolvedCountDiamond = 0;
        this.totalSolvedCountRuby = 0;
    }

    public Integer calcLevel(ProblemDto problem) {
        Integer level = problem.getLevel();

        if (level == 0) return 0; // Unknown;
        else if (level <= 5) return 1; // Bronze;
        else if (level <= 10) return 2; // Silver;
        else if (level <= 15) return 3; // Gold;
        else if (level <= 20) return 4; // Platinum;
        else if (level <= 25) return 5; // Diamond;
        else return 6; // Ruby;
    }

    public Integer calcLevel(SolvedProblemDto problem) {
        Integer level = problem.getTier();

        if (level == 0) return 0; // Unknown;
        else if (level <= 5) return 1; // Bronze;
        else if (level <= 10) return 2; // Silver;
        else if (level <= 15) return 3; // Gold;
        else if (level <= 20) return 4; // Platinum;
        else if (level <= 25) return 5; // Diamond;
        else return 6; // Ruby;
    }

    /*
     * 유저의 통계에 푼 문제를 추가한다.
     */
    public void addStat(ProblemDto problem) {
        Integer level = calcLevel(problem);
        // 문제 푼 수를 올린다.
        if (level == 1) {
            this.dailySolvedCountBronze++;
            this.weeklySolvedCountBronze++;
            this.totalSolvedCountBronze++;
        }
        else if (level == 2) {
            this.dailySolvedCountSilver++;
            this.weeklySolvedCountSilver++;
            this.totalSolvedCountSilver++;
        }
        else if (level == 3) {
            this.dailySolvedCountGold++;
            this.weeklySolvedCountGold++;
            this.totalSolvedCountGold++;
        }
        else if (level == 4) {
            this.dailySolvedCountPlatinum++;
            this.weeklySolvedCountPlatinum++;
            this.totalSolvedCountPlatinum++;
        }
        else if (level == 5) {
            this.dailySolvedCountDiamond++;
            this.weeklySolvedCountDiamond++;
            this.totalSolvedCountDiamond++;
        }
        else if (level == 6) {
            this.dailySolvedCountRuby++;
            this.weeklySolvedCountRuby++;
            this.totalSolvedCountRuby++;
        }
    }

    /*
     * 유저의 통계에 푼 문제를 추가한다.
     */
    public void addStat(SolvedProblemDto problem) {
        Integer level = calcLevel(problem);
        // 문제 푼 수를 올린다.
        if (level == 1) {
            this.dailySolvedCountBronze++;
            this.weeklySolvedCountBronze++;
            this.totalSolvedCountBronze++;
        }
        else if (level == 2) {
            this.dailySolvedCountSilver++;
            this.weeklySolvedCountSilver++;
            this.totalSolvedCountSilver++;
        }
        else if (level == 3) {
            this.dailySolvedCountGold++;
            this.weeklySolvedCountGold++;
            this.totalSolvedCountGold++;
        }
        else if (level == 4) {
            this.dailySolvedCountPlatinum++;
            this.weeklySolvedCountPlatinum++;
            this.totalSolvedCountPlatinum++;
        }
        else if (level == 5) {
            this.dailySolvedCountDiamond++;
            this.weeklySolvedCountDiamond++;
            this.totalSolvedCountDiamond++;
        }
        else if (level == 6) {
            this.dailySolvedCountRuby++;
            this.weeklySolvedCountRuby++;
            this.totalSolvedCountRuby++;
        }
    }

    /*
     * 유저의 통계에 푼 문제를 추가한다. (오늘만)
     */
    public void addStatDaily(SolvedProblemDto problem) {
        Integer level = calcLevel(problem);
        // 문제 푼 수를 올린다.
        if (level == 1) {
            this.dailySolvedCountBronze++;
        }
        else if (level == 2) {
            this.dailySolvedCountSilver++;
        }
        else if (level == 3) {
            this.dailySolvedCountGold++;
        }
        else if (level == 4) {
            this.dailySolvedCountPlatinum++;
        }
        else if (level == 5) {
            this.dailySolvedCountDiamond++;
        }
        else if (level == 6) {
            this.dailySolvedCountRuby++;
        }
    }


    /*
     * 일간 초기화
     */
    public void initDaily() {
        this.dailySolvedCountBronze = 0;
        this.dailySolvedCountSilver = 0;
        this.dailySolvedCountGold = 0;
        this.dailySolvedCountPlatinum = 0;
        this.dailySolvedCountDiamond = 0;
        this.dailySolvedCountRuby = 0;
    }

    /*
     * 주간 초기화
     */
    public void initWeekly() {
        this.dailySolvedCountBronze = 0;
        this.dailySolvedCountSilver = 0;
        this.dailySolvedCountGold = 0;
        this.dailySolvedCountPlatinum = 0;
        this.dailySolvedCountDiamond = 0;
        this.dailySolvedCountRuby = 0;
        this.weeklySolvedCountBronze = 0;
        this.weeklySolvedCountSilver = 0;
        this.weeklySolvedCountGold = 0;
        this.weeklySolvedCountPlatinum = 0;
        this.weeklySolvedCountDiamond = 0;
        this.weeklySolvedCountRuby = 0;
    }

    /*
     * 전체 초기화
     */
    public void initTotal() {
        this.dailySolvedCountBronze = 0;
        this.dailySolvedCountSilver = 0;
        this.dailySolvedCountGold = 0;
        this.dailySolvedCountPlatinum = 0;
        this.dailySolvedCountDiamond = 0;
        this.dailySolvedCountRuby = 0;
        this.weeklySolvedCountBronze = 0;
        this.weeklySolvedCountSilver = 0;
        this.weeklySolvedCountGold = 0;
        this.weeklySolvedCountPlatinum = 0;
        this.weeklySolvedCountDiamond = 0;
        this.weeklySolvedCountRuby = 0;
        this.totalSolvedCountBronze = 0;
        this.totalSolvedCountSilver = 0;
        this.totalSolvedCountGold = 0;
        this.totalSolvedCountPlatinum = 0;
        this.totalSolvedCountDiamond = 0;
        this.totalSolvedCountRuby = 0;
    }
}
