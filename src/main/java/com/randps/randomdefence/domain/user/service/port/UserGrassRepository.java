package com.randps.randomdefence.domain.user.service.port;

import com.randps.randomdefence.domain.user.domain.UserGrass;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import java.util.List;
import java.util.Optional;

public interface UserGrassRepository {

    Optional<UserGrass> findByUserRandomStreakAndDate(UserRandomStreak userRandomStreak, String date);
    List<UserGrass> findAllByUserRandomStreakAndGrassInfo(UserRandomStreak userRandomStreak, Boolean grassInfo);
    UserGrass save(UserGrass userGrass);

}
