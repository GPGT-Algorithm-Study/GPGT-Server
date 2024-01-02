package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.service.port.UserRandomStreakRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRandomStreakRepositoryAdapter implements UserRandomStreakRepository {

    private final UserRandomStreakJpaRepository userRandomStreakJpaRepository;

    @Override
    public Optional<UserRandomStreak> findByBojHandle(String bojHandle) {
        return userRandomStreakJpaRepository.findByBojHandle(bojHandle);
    }

    @Override
    public UserRandomStreak save(UserRandomStreak userRandomStreak) {
        return userRandomStreakJpaRepository.save(userRandomStreak);
    }

    @Override
    public List<UserRandomStreak> findAll() {
        return userRandomStreakJpaRepository.findAll();
    }

    @Override
    public void delete(UserRandomStreak userRandomStreak) {
        userRandomStreakJpaRepository.delete(userRandomStreak);
    }
}
