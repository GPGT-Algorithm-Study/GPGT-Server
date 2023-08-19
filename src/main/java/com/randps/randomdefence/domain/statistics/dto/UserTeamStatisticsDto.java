package com.randps.randomdefence.domain.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserTeamStatisticsDto {

    private String bojHandle;

    private String notionId;

    private String profileImg;

    private Integer point;

    @Builder
    public UserTeamStatisticsDto(String bojHandle, String notionId, String profileImg, Integer point) {
        this.bojHandle = bojHandle;
        this.notionId = notionId;
        this.profileImg = profileImg;
        this.point = point;
    }

    public UserTeamStatisticsDto() {
        this.bojHandle = "";
        this.notionId = "";
        this.profileImg = "null";
        this.point = 0;
    }

}
