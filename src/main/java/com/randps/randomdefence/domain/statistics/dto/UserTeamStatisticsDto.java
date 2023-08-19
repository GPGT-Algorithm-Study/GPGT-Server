package com.randps.randomdefence.domain.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserTeamStatisticsDto {

    private String bojHandle;

    private String notionId;

    private String profileImg;

    private String emoji;

    private Integer point;

    @Builder
    public UserTeamStatisticsDto(String bojHandle, String notionId, String profileImg, String emoji, Integer point) {
        this.bojHandle = bojHandle;
        this.notionId = notionId;
        this.profileImg = profileImg;
        this.emoji = emoji;
        this.point = point;
    }

    public UserTeamStatisticsDto() {
        this.bojHandle = "";
        this.notionId = "";
        this.profileImg = "null";
        this.emoji = "🧐";
        this.point = 0;
    }

}
