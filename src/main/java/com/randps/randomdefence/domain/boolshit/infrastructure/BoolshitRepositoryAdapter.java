package com.randps.randomdefence.domain.boolshit.infrastructure;

import com.randps.randomdefence.domain.boolshit.domain.Boolshit;
import com.randps.randomdefence.domain.boolshit.dto.BoolshitLastResponse;
import com.randps.randomdefence.domain.boolshit.service.port.BoolshitRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoolshitRepositoryAdapter implements BoolshitRepository {

    private final BoolshitJpaRepository boolshitJpaRepository;

    private final BoolshitRepositoryCustom boolshitRepositoryCustom;

    @Override
    public Optional<BoolshitLastResponse> findLastBoolshit() {
        return boolshitRepositoryCustom.findLastBoolshit();
    }

    @Override
    public List<Boolshit> findAll() {
        return boolshitJpaRepository.findAll();
    }

    @Override
    public Boolshit save(Boolshit boolshit) {
        return boolshitJpaRepository.save(boolshit);
    }

    @Override
    public void deleteAllByBojHandle(String bojHandle) {
        boolshitJpaRepository.deleteAllByBojHandle(bojHandle);
    }

}
