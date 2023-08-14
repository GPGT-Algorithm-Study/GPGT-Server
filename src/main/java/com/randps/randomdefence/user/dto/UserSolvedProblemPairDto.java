package com.randps.randomdefence.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UserSolvedProblemPairDto {
    private String bojHandle;

    private List<SolvedProblemDto> solvedProblemList;

    @Builder
    public UserSolvedProblemPairDto(String bojHandle, List<SolvedProblemDto> solvedProblemList) {
        this.bojHandle = bojHandle;
        this.solvedProblemList = solvedProblemList;
    }
}
