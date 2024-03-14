package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.domain.user.dto.UserRandomStreakDto;
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
@Table(name = "RD_USER_RANDOM_STREAK")
@Entity
public class UserRandomStreak extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private String startLevel;

    private String endLevel;

    private Boolean isKo;

    private Integer todayRandomProblemId;

    private Boolean isTodayRandomSolved;

    private Integer currentRandomStreak;

    private Integer maxRandomStreak;

    @Builder
    public UserRandomStreak(Long id, String bojHandle, String startLevel, String endLevel, Boolean isKo, Integer todayRandomProblemId, Boolean isTodayRandomSolved, Integer currentRandomStreak, Integer maxRandomStreak) {
        this.id = id;
        this.bojHandle = bojHandle;
        this.startLevel = startLevel;
        this.endLevel = endLevel;
        this.isKo = isKo;
        this.todayRandomProblemId = todayRandomProblemId;
        this.isTodayRandomSolved = isTodayRandomSolved;
        this.currentRandomStreak = currentRandomStreak;
        this.maxRandomStreak = maxRandomStreak;
    }

    public Boolean updateLevel(String startLevel, String endLevel, Boolean isKo) {
        Boolean isSetup = (this.startLevel.equals("") && this.endLevel.equals(""));
        this.startLevel = startLevel;
        this.endLevel = endLevel;
        this.isKo = isKo;
        return isSetup;
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
                .isKo(this.isKo)
                .todayRandomProblemId(this.todayRandomProblemId)
                .isTodayRandomSolved(this.isTodayRandomSolved)
                .currentRandomStreak(this.currentRandomStreak)
                .maxRandomStreak(this.maxRandomStreak)
                .build();
    }
}
