package com.randps.randomdefence.domain.problem.infrastructure;

import com.randps.randomdefence.domain.problem.domain.Problem;
import com.randps.randomdefence.domain.problem.service.port.ProblemRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemRepositoryAdapter implements ProblemRepository {

    private final ProblemJpaRepository problemJpaRepository;

    @Override
    public Optional<Problem> findByProblemId(Integer problemId) {
        return problemJpaRepository.findByProblemId(problemId);
    }

    @Override
    public Problem save(Problem problem) {
        return problemJpaRepository.save(problem);
    }

}
