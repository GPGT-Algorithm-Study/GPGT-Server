package com.randps.randomdefence.domain.statistics.dto;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SolvedBarDto {
    private User user;

    private UserProblemStatistics userProblemStatistics;

    @Builder
    public SolvedBarDto(User user, UserProblemStatistics userProblemStatistics) {
        this.user = user;
        this.userProblemStatistics = userProblemStatistics;
    }
}
