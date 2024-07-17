package com.randps.randomdefence.domain.user.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

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
