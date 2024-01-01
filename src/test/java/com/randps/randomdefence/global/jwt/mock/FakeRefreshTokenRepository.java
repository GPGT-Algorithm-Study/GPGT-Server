package com.randps.randomdefence.global.jwt.mock;

import com.randps.randomdefence.global.jwt.component.port.RefreshTokenRepository;
import com.randps.randomdefence.global.jwt.domain.RefreshToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeRefreshTokenRepository implements RefreshTokenRepository {

    private final List<RefreshToken> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public Optional<RefreshToken> findByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).findAny();
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        if (refreshToken.getId() == null || refreshToken.getId() == 0L) {
            autoIncreasingCount++;
            RefreshToken newRefreshToken = RefreshToken.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(refreshToken.getBojHandle())
                    .token(refreshToken.getRefreshToken())
                    .build();
            data.add(newRefreshToken);
            return newRefreshToken;
        } else {
            data.removeIf(item -> item.getId().equals(refreshToken.getId()));
            RefreshToken newRefreshToken = RefreshToken.builder()
                    .id(refreshToken.getId())
                    .bojHandle(refreshToken.getBojHandle())
                    .token(refreshToken.getRefreshToken())
                    .build();
            data.add(newRefreshToken);
            return newRefreshToken;
        }
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        data.removeIf(item -> item.getBojHandle().equals(refreshToken.getBojHandle()));
    }
}
