package com.randps.randomdefence.user.domain;

import com.randps.randomdefence.user.domain.UserSolvedProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSolvedProblemRepository extends JpaRepository<UserSolvedProblem, Long> {
    List<UserSolvedProblem> findAllByBojHandle(String bojHandle);

}
