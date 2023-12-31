package com.randps.randomdefence.domain.problem.service.port;

import com.randps.randomdefence.domain.problem.domain.Problem;
import java.util.Optional;

public interface ProblemRepository {

    Optional<Problem> findByProblemId(Integer problemId);
    Problem save(Problem problem);

}
