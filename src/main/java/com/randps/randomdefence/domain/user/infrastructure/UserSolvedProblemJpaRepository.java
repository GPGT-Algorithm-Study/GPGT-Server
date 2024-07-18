package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserSolvedProblemJpaRepository extends JpaRepository<UserSolvedProblem, Long> {
    List<UserSolvedProblem> findAllByBojHandle(String bojHandle);

    Optional<UserSolvedProblem> findByBojHandleAndProblemId(String bojHandle, Integer problemId);

    List<UserSolvedProblem> findAllByCreatedDateAfter(LocalDateTime createdDate);

    void deleteAllByBojHandle(String bojHandle);
}
