package com.randps.randomdefence.global.jwt.intrastructure;

import com.randps.randomdefence.global.jwt.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByBojHandle(String bojHandle);

    void deleteByBojHandle(String bojHandle);
}