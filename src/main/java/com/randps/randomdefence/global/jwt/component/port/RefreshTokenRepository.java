package com.randps.randomdefence.global.jwt.component.port;

import com.randps.randomdefence.global.jwt.domain.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByBojHandle(String bojHandle);

    RefreshToken save(RefreshToken refreshToken);

    void delete(RefreshToken refreshToken);

}
