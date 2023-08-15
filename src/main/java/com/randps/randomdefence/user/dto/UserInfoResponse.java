package com.randps.randomdefence.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoResponse {

    private String bojHandle;

    private String notionId;

    private Boolean manager;

    private Integer warning;

    private String profileImg;

    private String emoji;

    private Integer tier;

    private Integer totalSolved;

    private Integer currentStreak;

    private Integer currentRandomStreak;

    private Integer team;

    private Integer point;

    private Boolean isTodaySolved;

    private Boolean isTodayRandomSolved;

    private Integer todaySolvedProblemCount;

    @Builder
    public UserInfoResponse(String bojHandle, String notionId, Boolean manager, Integer warning, String profileImg, String emoji, Integer tier, Integer totalSolved, Integer currentStreak, Integer currentRandomStreak, Integer team, Integer point, Boolean isTodaySolved, Boolean isTodayRandomSolved, Integer todaySolvedProblemCount) {
        this.bojHandle = bojHandle;
        this.notionId = notionId;
        this.manager = manager;
        this.warning = warning;
        this.profileImg = profileImg;
        this.emoji = emoji;
        this.tier = tier;
        this.totalSolved = totalSolved;
        this.currentStreak = currentStreak;
        this.currentRandomStreak = currentRandomStreak;
        this.team = team;
        this.point = point;
        this.isTodaySolved = isTodaySolved;
        this.isTodayRandomSolved = isTodayRandomSolved;
        this.todaySolvedProblemCount = todaySolvedProblemCount;
    }


    public UserInfoResponse() {
        this.bojHandle = "";
        this.notionId = "";
        this.manager = false;
        this.warning = 0;
        this.profileImg = "";
        this.emoji = "";
        this.tier = 0;
        this.totalSolved = 0;
        this.currentStreak = 0;
        this.currentRandomStreak = 0;
        this.team = 0;
        this.point = 0;
        this.isTodaySolved = false;
        this.isTodayRandomSolved = false;
        this.todaySolvedProblemCount = 0;
    }
}
