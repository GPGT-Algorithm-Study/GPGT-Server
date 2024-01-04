package com.randps.randomdefence.domain.boolshit.service.port;

import com.randps.randomdefence.domain.boolshit.domain.Boolshit;
import com.randps.randomdefence.domain.boolshit.dto.BoolshitLastResponse;
import java.util.List;
import java.util.Optional;

public interface BoolshitRepository {

    Optional<BoolshitLastResponse> findLastBoolshit();

    List<Boolshit> findAll();

    Boolshit save(Boolshit boolshit);

    void deleteAllByBojHandle(String bojHandle);
}
