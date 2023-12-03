package com.randps.randomdefence.domain.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MostSolvedProblemDto {

    private Integer problemId;

    private Long solvedCount;

}
