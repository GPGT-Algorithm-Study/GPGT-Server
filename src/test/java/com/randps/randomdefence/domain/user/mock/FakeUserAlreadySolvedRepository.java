package com.randps.randomdefence.domain.user.mock;

import com.randps.randomdefence.domain.user.domain.UserAlreadySolved;
import com.randps.randomdefence.domain.user.service.port.UserAlreadySolvedRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeUserAlreadySolvedRepository implements UserAlreadySolvedRepository {

    private final List<UserAlreadySolved> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public Optional<UserAlreadySolved> findByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).findAny();
    }

    @Override
    public UserAlreadySolved save(UserAlreadySolved userAlreadySolved) {
        if (userAlreadySolved.getId() == null || userAlreadySolved.getId() == 0L) {
            autoIncreasingCount++;
            UserAlreadySolved newUserAlreadySolved = UserAlreadySolved.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(userAlreadySolved.getBojHandle())
                    .alreadySolvedList(userAlreadySolved.getAlreadySolvedList())
                    .build();
            data.add(newUserAlreadySolved);
            return newUserAlreadySolved;
        } else {
            data.removeIf(item -> item.getId().equals(userAlreadySolved.getId()));
            UserAlreadySolved newUserAlreadySolved = UserAlreadySolved.builder()
                    .id(userAlreadySolved.getId())
                    .bojHandle(userAlreadySolved.getBojHandle())
                    .alreadySolvedList(userAlreadySolved.getAlreadySolvedList())
                    .build();
            data.add(newUserAlreadySolved);
            return newUserAlreadySolved;
        }
    }

    @Override
    public List<UserAlreadySolved> findAll() {
        return data;
    }
}
