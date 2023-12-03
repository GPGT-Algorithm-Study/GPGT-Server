package com.randps.randomdefence.domain.statistics.domain;

import com.randps.randomdefence.domain.statistics.dto.MostSolvedProblemDto;
import java.util.List;

public interface ProblemStatisticsRepositoryCustom {

    List<MostSolvedProblemDto> findAllMostSolvedProblems();

}
