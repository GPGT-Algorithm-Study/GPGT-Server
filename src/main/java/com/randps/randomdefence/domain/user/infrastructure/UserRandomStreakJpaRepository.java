package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRandomStreakJpaRepository extends JpaRepository<UserRandomStreak, Long> {
    Optional<UserRandomStreak> findByBojHandle(String bojHandle);
}
