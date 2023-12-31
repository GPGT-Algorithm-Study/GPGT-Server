package com.randps.randomdefence.domain.user.mock;

import com.randps.randomdefence.domain.user.domain.UserGrass;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.service.port.UserGrassRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeUserGrassRepository implements UserGrassRepository {

    private final List<UserGrass> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;
    @Override
    public Optional<UserGrass> findByUserRandomStreakAndDate(UserRandomStreak userRandomStreak, String date) {
        return data.stream().filter(item -> item.getUserRandomStreak().getId().equals(userRandomStreak.getId()) && item.getDate().equals(date)).findAny();
    }

    @Override
    public List<UserGrass> findAllByUserRandomStreakAndGrassInfo(UserRandomStreak userRandomStreak, Boolean grassInfo) {
        return data.stream().filter(item -> item.getUserRandomStreak().getId().equals(userRandomStreak.getId()) && item.getGrassInfo().equals(grassInfo)).collect(Collectors.toList());
    }

    @Override
    public UserGrass save(UserGrass userGrass) {
        if (userGrass.getId() == null || userGrass.getId() == 0L) {
            autoIncreasingCount++;
            UserGrass newUserGrass = UserGrass.builder()
                    .id(userGrass.getId())
                    .problemId(userGrass.getProblemId())
                    .date(userGrass.getDate())
                    .grassInfo(userGrass.getGrassInfo())
                    .build();
            data.add(newUserGrass);
            return newUserGrass;
        } else {
            data.removeIf(item -> item.getId().equals(userGrass.getId()));
            UserGrass newUserGrass = UserGrass.builder()
                    .id(userGrass.getId())
                    .problemId(userGrass.getProblemId())
                    .date(userGrass.getDate())
                    .grassInfo(userGrass.getGrassInfo())
                    .build();
            data.add(newUserGrass);
            return newUserGrass;
        }
    }
}
