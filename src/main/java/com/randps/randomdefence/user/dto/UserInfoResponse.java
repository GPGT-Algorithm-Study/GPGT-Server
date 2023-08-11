package com.randps.randomdefence.user.dto;

import lombok.Data;

@Data
public class UserInfoResponse {
    public String bojHandle;
    public String profileImg;
    public Integer currentStreak;
    public Integer maxStreak;
}
