package com.randps.randomdefence.domain.statistics.domain;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "RD_STATISTICS_USER")
@Entity
public class UserStatistics extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private Integer dailySolvedProblemCount;

    private Integer dailySolvedMostDifficultProblemId;

    private Integer dailySolvedMostDifficult;

    private Integer dailyEarningPoint;

    private Integer weeklySolvedProblemCount;

    private Integer weeklySolvedMostDifficultProblemId;

    private Integer weeklySolvedMostDifficult;

    private Integer weeklyEarningPoint;

    private Integer totalSolvedProblemCount;

    private Integer totalSolvedMostDifficultProblemId;

    private Integer totalSolvedMostDifficult;

    private Integer totalEarningPoint;

    @Builder
    public UserStatistics(Long id, String bojHandle, Integer dailySolvedProblemCount, Integer dailySolvedMostDifficultProblemId, Integer dailySolvedMostDifficult, Integer dailyEarningPoint, Integer weeklySolvedProblemCount, Integer weeklySolvedMostDifficultProblemId, Integer weeklySolvedMostDifficult, Integer weeklyEarningPoint, Integer totalSolvedProblemCount, Integer totalSolvedMostDifficultProblemId, Integer totalSolvedMostDifficult, Integer totalEarningPoint) {
        this.id = id;
        this.bojHandle = bojHandle;
        this.dailySolvedProblemCount = dailySolvedProblemCount;
        this.dailySolvedMostDifficultProblemId = dailySolvedMostDifficultProblemId;
        this.dailySolvedMostDifficult = dailySolvedMostDifficult;
        this.dailyEarningPoint = dailyEarningPoint;
        this.weeklySolvedProblemCount = weeklySolvedProblemCount;
        this.weeklySolvedMostDifficultProblemId = weeklySolvedMostDifficultProblemId;
        this.weeklySolvedMostDifficult = weeklySolvedMostDifficult;
        this.weeklyEarningPoint = weeklyEarningPoint;
        this.totalSolvedProblemCount = totalSolvedProblemCount;
        this.totalSolvedMostDifficultProblemId = totalSolvedMostDifficultProblemId;
        this.totalSolvedMostDifficult = totalSolvedMostDifficult;
        this.totalEarningPoint = totalEarningPoint;
    }

    public UserStatistics(String bojHandle) {
        this.bojHandle = bojHandle;
        this.dailySolvedProblemCount = 0;
        this.dailySolvedMostDifficultProblemId = 0;
        this.dailySolvedMostDifficult = 0;
        this.dailyEarningPoint = 0;
        this.weeklySolvedProblemCount = 0;
        this.weeklySolvedMostDifficultProblemId = 0;
        this.weeklySolvedMostDifficult = 0;
        this.weeklyEarningPoint = 0;
        this.totalSolvedProblemCount = 0;
        this.totalSolvedMostDifficultProblemId = 0;
        this.totalSolvedMostDifficult = 0;
        this.totalEarningPoint = 0;
    }

    /*
     * 유저의 통계에 푼 문제를 추가한다.
     */
    public void addStat(ProblemDto problem, Integer earningPoint) {
        // 푼 문제의 개수를 늘린다.
        this.dailySolvedProblemCount++;
        this.weeklySolvedProblemCount++;
        this.totalSolvedProblemCount++;

        // 푼 문제의 가장 어려운 난이도를 갱신한다.
        if (this.dailySolvedMostDifficult < problem.getLevel()) {
            this.dailySolvedMostDifficult = problem.getLevel();
            this.dailySolvedMostDifficultProblemId = problem.getProblemId();
        }
        if (this.weeklySolvedMostDifficult < problem.getLevel()) {
            this.weeklySolvedMostDifficult = problem.getLevel();
            this.weeklySolvedMostDifficultProblemId = problem.getProblemId();
        }
        if (this.totalSolvedMostDifficult < problem.getLevel()) {
            this.totalSolvedMostDifficult = problem.getLevel();
            this.totalSolvedMostDifficultProblemId = problem.getProblemId();
        }

        // 통계의 포인트를 늘린다.
        this.dailyEarningPoint += earningPoint;
        this.weeklyEarningPoint += earningPoint;
        this.totalEarningPoint += earningPoint;
    }

    /*
     * 유저의 통계에 푼 문제를 추가한다. (오늘에만)
     */
    public void addStatDaily(SolvedProblemDto problem, Integer earningPoint) {
        // 푼 문제의 개수를 늘린다.
        this.dailySolvedProblemCount++;

        // 푼 문제의 가장 어려운 난이도를 갱신한다.
        if (this.dailySolvedMostDifficult < problem.getTier()) {
            this.dailySolvedMostDifficult = problem.getTier();
            this.dailySolvedMostDifficultProblemId = problem.getProblemId();
        }

        // 통계의 포인트를 늘린다.
        this.dailyEarningPoint += earningPoint;
    }

    /*
     * 일간 초기화
     */
    public void initDaily() {
        this.dailySolvedProblemCount = 0;
        this.dailySolvedMostDifficultProblemId = 0;
        this.dailySolvedMostDifficult = 0;
        this.dailyEarningPoint = 0;
    }

    /*
     * 주간 초기화
     */
    public void initWeekly() {
        this.dailySolvedProblemCount = 0;
        this.dailySolvedMostDifficultProblemId = 0;
        this.dailySolvedMostDifficult = 0;
        this.dailyEarningPoint = 0;
        this.weeklySolvedProblemCount = 0;
        this.weeklySolvedMostDifficultProblemId = 0;
        this.weeklySolvedMostDifficult = 0;
        this.weeklyEarningPoint = 0;
    }

    /*
     * 전체 초기화
     */
    public void initTotal() {
        this.dailySolvedProblemCount = 0;
        this.dailySolvedMostDifficultProblemId = 0;
        this.dailySolvedMostDifficult = 0;
        this.dailyEarningPoint = 0;
        this.weeklySolvedProblemCount = 0;
        this.weeklySolvedMostDifficultProblemId = 0;
        this.weeklySolvedMostDifficult = 0;
        this.weeklyEarningPoint = 0;
        this.totalSolvedProblemCount = 0;
        this.totalSolvedMostDifficultProblemId = 0;
        this.totalSolvedMostDifficult = 0;
        this.totalEarningPoint = 0;
    }
}
