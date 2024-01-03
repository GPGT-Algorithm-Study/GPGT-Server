package com.randps.randomdefence.domain.problem.infrastructure;

import com.randps.randomdefence.domain.problem.domain.Problem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemJpaRepository extends JpaRepository<Problem, Long> {
    Optional<Problem> findByProblemId(Integer problemId);
}
