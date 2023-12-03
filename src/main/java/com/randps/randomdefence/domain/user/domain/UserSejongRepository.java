package com.randps.randomdefence.domain.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSejongRepository extends JpaRepository<UserSejong, Long> {

    Optional<UserSejong> findByBojHandle(String bojHandle);

}
