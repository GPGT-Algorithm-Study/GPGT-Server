package com.randps.randomdefence.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserGrassDto {

    private Integer problemId;

    private String date;

    private Boolean grassInfo;

    @Builder
    public UserGrassDto(Integer problemId, String date, Boolean grassInfo) {
        this.problemId = problemId;
        this.date = date;
        this.grassInfo = grassInfo;
    }
}
