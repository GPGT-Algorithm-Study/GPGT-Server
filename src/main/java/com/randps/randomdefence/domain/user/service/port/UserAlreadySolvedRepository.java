package com.randps.randomdefence.domain.user.service.port;

import com.randps.randomdefence.domain.user.domain.UserAlreadySolved;
import java.util.List;
import java.util.Optional;

public interface UserAlreadySolvedRepository {
    Optional<UserAlreadySolved> findByBojHandle(String bojHandle);
    UserAlreadySolved save(UserAlreadySolved userAlreadySolved);
    List<UserAlreadySolved> findAll();
}
