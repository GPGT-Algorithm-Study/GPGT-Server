package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserAlreadySolved;
import com.randps.randomdefence.domain.user.service.port.UserAlreadySolvedRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAlreadySolvedRepositoryAdapter implements UserAlreadySolvedRepository {

    private final UserAlreadySolvedJpaRepository userAlreadySolvedJpaRepository;

    @Override
    public Optional<UserAlreadySolved> findByBojHandle(String bojHandle) {
        return userAlreadySolvedJpaRepository.findByBojHandle(bojHandle);
    }

    @Override
    public UserAlreadySolved save(UserAlreadySolved userAlreadySolved) {
        return userAlreadySolvedJpaRepository.save(userAlreadySolved);
    }

    @Override
    public List<UserAlreadySolved> findAll() {
        return userAlreadySolvedJpaRepository.findAll();
    }
}
