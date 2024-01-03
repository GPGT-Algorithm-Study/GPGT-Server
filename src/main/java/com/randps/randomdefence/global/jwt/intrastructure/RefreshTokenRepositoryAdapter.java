package com.randps.randomdefence.global.jwt.intrastructure;

import com.randps.randomdefence.global.jwt.component.port.RefreshTokenRepository;
import com.randps.randomdefence.global.jwt.domain.RefreshToken;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public Optional<RefreshToken> findByBojHandle(String bojHandle) {
        return refreshTokenJpaRepository.findByBojHandle(bojHandle);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(refreshToken);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenJpaRepository.delete(refreshToken);
    }

    @Override
    public void deleteByBojHandle(String bojHandle) {
        refreshTokenJpaRepository.deleteByBojHandle(bojHandle);
    }

}
