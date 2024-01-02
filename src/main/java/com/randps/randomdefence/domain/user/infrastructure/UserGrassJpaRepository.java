package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserGrass;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGrassJpaRepository extends JpaRepository<UserGrass, Long> {
    Optional<UserGrass> findByUserRandomStreakAndDate(UserRandomStreak userRandomStreak, String date);
    List<UserGrass> findAllByUserRandomStreakAndGrassInfo(UserRandomStreak userRandomStreak, Boolean grassInfo);

    List<UserGrass> findAllByUserRandomStreak(UserRandomStreak userRandomStreak);

    void deleteAll(List<UserGrass> userGrasses);
}
