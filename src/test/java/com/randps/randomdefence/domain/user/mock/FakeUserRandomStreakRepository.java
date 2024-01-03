package com.randps.randomdefence.domain.user.mock;

import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.service.port.UserRandomStreakRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeUserRandomStreakRepository implements UserRandomStreakRepository {

    private final List<UserRandomStreak> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public Optional<UserRandomStreak> findByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).findAny();
    }

    @Override
    public UserRandomStreak save(UserRandomStreak userRandomStreak) {
        if (userRandomStreak.getId() == null || userRandomStreak.getId() == 0L) {
            autoIncreasingCount++;
            UserRandomStreak newUserRandomStreak = UserRandomStreak.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(userRandomStreak.getBojHandle())
                    .startLevel(userRandomStreak.getStartLevel())
                    .endLevel(userRandomStreak.getEndLevel())
                    .isKo(userRandomStreak.getIsKo())
                    .todayRandomProblemId(userRandomStreak.getTodayRandomProblemId())
                    .isTodayRandomSolved(userRandomStreak.getIsTodayRandomSolved())
                    .currentRandomStreak(userRandomStreak.getCurrentRandomStreak())
                    .maxRandomStreak(userRandomStreak.getMaxRandomStreak())
                    .build();
            data.add(newUserRandomStreak);
            return newUserRandomStreak;
        } else {
            data.removeIf(item -> item.getId().equals(userRandomStreak.getId()));
            UserRandomStreak newUserRandomStreak = UserRandomStreak.builder()
                    .id(userRandomStreak.getId())
                    .bojHandle(userRandomStreak.getBojHandle())
                    .startLevel(userRandomStreak.getStartLevel())
                    .endLevel(userRandomStreak.getEndLevel())
                    .isKo(userRandomStreak.getIsKo())
                    .todayRandomProblemId(userRandomStreak.getTodayRandomProblemId())
                    .isTodayRandomSolved(userRandomStreak.getIsTodayRandomSolved())
                    .currentRandomStreak(userRandomStreak.getCurrentRandomStreak())
                    .maxRandomStreak(userRandomStreak.getMaxRandomStreak())
                    .build();
            data.add(newUserRandomStreak);
            return newUserRandomStreak;
        }
    }

    @Override
    public List<UserRandomStreak> findAll() {
        return data;
    }

    @Override
    public void delete(UserRandomStreak userRandomStreak) {
        data.removeIf(item -> item.getId().equals(userRandomStreak.getId()));
    }
}
