package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserAlreadySolved;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAlreadySolvedJpaRepository extends JpaRepository<UserAlreadySolved, Long> {
    Optional<UserAlreadySolved> findByBojHandle(String bojHandle);
}
