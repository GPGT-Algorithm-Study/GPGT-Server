package com.randps.randomdefence.user.domain;

import com.randps.randomdefence.user.dto.UserRandomStreakDto;
import com.randps.randomdefence.user.dto.UserRandomStreakResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_USER_RANDOM_STREAK")
@Entity
public class UserRandomStreak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private String startLevel;

    private String endLevel;

    private Integer todayRandomProblemId;

    private Boolean isTodayRandomSolved;

    private Integer currentRandomStreak;

    private Integer maxRandomStreak;

    @Builder
    public UserRandomStreak(String bojHandle, String startLevel, String endLevel, Integer todayRandomProblemId, Boolean isTodayRandomSolved, Integer currentRandomStreak, Integer maxRandomStreak) {
        this.bojHandle = bojHandle;
        this.startLevel = startLevel;
        this.endLevel = endLevel;
        this.todayRandomProblemId = todayRandomProblemId;
        this.isTodayRandomSolved = isTodayRandomSolved;
        this.currentRandomStreak = currentRandomStreak;
        this.maxRandomStreak = maxRandomStreak;
    }

    public void updateLevel(String startLevel, String endLevel) {
        this.startLevel = startLevel;
        this.endLevel = endLevel;
    }

    public void increaseCurrentStreak() {
        this.currentRandomStreak++;
        if (currentRandomStreak > maxRandomStreak)
            maxRandomStreak = currentRandomStreak;
    }

    public void resetCurrentStreak() {
        this.currentRandomStreak = 0;
    }

    public void solvedCheckOk() {
        this.isTodayRandomSolved = true;
        increaseCurrentStreak();
    }

    public void setTodayRandomProblemId(Integer problemId) {
        this.todayRandomProblemId = problemId;
        this.isTodayRandomSolved = false;
    }

    public UserRandomStreakDto toDto() {
        return UserRandomStreakDto.builder()
                .bojHandle(this.bojHandle)
                .startLevel(this.startLevel)
                .endLevel(this.endLevel)
                .todayRandomProblemId(this.todayRandomProblemId)
                .isTodayRandomSolved(this.isTodayRandomSolved)
                .currentRandomStreak(this.currentRandomStreak)
                .maxRandomStreak(this.maxRandomStreak)
                .build();
    }
}
