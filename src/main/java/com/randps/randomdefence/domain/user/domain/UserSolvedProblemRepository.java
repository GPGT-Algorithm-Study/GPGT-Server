package com.randps.randomdefence.domain.user.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSolvedProblemRepository extends JpaRepository<UserSolvedProblem, Long> {
    List<UserSolvedProblem> findAllByBojHandle(String bojHandle);

    Optional<UserSolvedProblem> findByBojHandleAndProblemId(String bojHandle, Integer problemId);

}
