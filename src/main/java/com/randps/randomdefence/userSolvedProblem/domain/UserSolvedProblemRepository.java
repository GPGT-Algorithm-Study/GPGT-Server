package com.randps.randomdefence.userSolvedProblem.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSolvedProblemRepository extends JpaRepository<UserSolvedProblem, Long> {
    List<UserSolvedProblem> findAllByBojHandle(String bojHandle);

}
