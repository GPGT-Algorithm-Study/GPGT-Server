package com.randps.randomdefence.domain.statistics.dto;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RecentlyTodaySolvedProblemResponseDto {

    private LocalDateTime solvedAt;

    private String bojHandle;

    private String notionId;

    private String profileImg;

    private String emoji;

    private ProblemDto problem;

    @Builder
    public RecentlyTodaySolvedProblemResponseDto(LocalDateTime solvedAt, String bojHandle, String notionId, String profileImg, String emoji, ProblemDto problem) {
        this.solvedAt = solvedAt;
        this.bojHandle = bojHandle;
        this.notionId = notionId;
        this.profileImg = profileImg;
        this.emoji = emoji;
        this.problem = problem;
    }

}
