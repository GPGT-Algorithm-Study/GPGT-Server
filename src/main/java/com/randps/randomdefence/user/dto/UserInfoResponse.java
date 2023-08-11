package com.randps.randomdefence.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoResponse {
    public String bojHandle;
    public String userTier;
    public String notionId;
    public String profileImg;
    public Integer currentStreak;
    public Integer warning;
    public Boolean isManager;
    public Boolean isTodaySolved;

    @Builder
    public UserInfoResponse(String bojHandle, String userTier, String notionId, String profileImg, Integer currentStreak, Integer warning, Boolean isManager, Boolean isTodaySolved) {
        this.bojHandle = bojHandle;
        this.userTier = userTier;
        this.notionId = notionId;
        this.profileImg = profileImg;
        this.currentStreak = currentStreak;
        this.warning = warning;
        this.isManager = isManager;
        this.isTodaySolved = isTodaySolved;
    }

    public UserInfoResponse() {
        this.bojHandle = "";
        this.userTier = "";
        this.notionId = "";
        this.profileImg = "";
        this.currentStreak = 0;
        this.warning = 0;
        this.isManager = false;
        this.isTodaySolved = false;
    }
}
