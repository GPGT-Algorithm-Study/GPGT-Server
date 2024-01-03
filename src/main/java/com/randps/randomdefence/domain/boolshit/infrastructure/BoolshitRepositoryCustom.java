package com.randps.randomdefence.domain.boolshit.infrastructure;

import com.randps.randomdefence.domain.boolshit.dto.BoolshitLastResponse;
import java.util.Optional;

public interface BoolshitRepositoryCustom {
    Optional<BoolshitLastResponse> findLastBoolshit();
}
