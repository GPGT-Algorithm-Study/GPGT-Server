package com.randps.randomdefence.domain.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAlreadySolvedRepository extends JpaRepository<UserAlreadySolved, Long> {
    Optional<UserAlreadySolved> findByBojHandle(String bojHandle);
}
