package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByBojHandle(String bojHandle);

    List<User> findAllByTeam(Integer team);

}
