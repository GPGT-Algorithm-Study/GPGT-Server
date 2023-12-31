package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserGrass;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.service.port.UserGrassRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserGrassRepositoryAdapter implements UserGrassRepository {

    private final UserGrassJpaRepository userGrassJpaRepository;


    @Override
    public Optional<UserGrass> findByUserRandomStreakAndDate(UserRandomStreak userRandomStreak, String date) {
        return userGrassJpaRepository.findByUserRandomStreakAndDate(userRandomStreak, date);
    }

    @Override
    public List<UserGrass> findAllByUserRandomStreakAndGrassInfo(UserRandomStreak userRandomStreak, Boolean grassInfo) {
        return userGrassJpaRepository.findAllByUserRandomStreakAndGrassInfo(userRandomStreak, grassInfo);
    }

    @Override
    public UserGrass save(UserGrass userGrass) {
        return userGrassJpaRepository.save(userGrass);
    }
}
