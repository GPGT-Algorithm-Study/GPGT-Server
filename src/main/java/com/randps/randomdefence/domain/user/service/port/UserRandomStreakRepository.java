package com.randps.randomdefence.domain.user.service.port;

import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import java.util.List;
import java.util.Optional;

public interface UserRandomStreakRepository {
    Optional<UserRandomStreak> findByBojHandle(String bojHandle);

    UserRandomStreak save(UserRandomStreak userRandomStreak);

    List<UserRandomStreak> findAll();
}
