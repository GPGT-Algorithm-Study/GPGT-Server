package com.randps.randomdefence.domain.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByBojHandle(String bojHandle);

    List<User> findAllByTeam(Integer team);

}
