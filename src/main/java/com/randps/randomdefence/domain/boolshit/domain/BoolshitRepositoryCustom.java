package com.randps.randomdefence.domain.boolshit.domain;

import com.randps.randomdefence.domain.boolshit.dto.BoolshitLastResponse;

import java.util.Optional;

public interface BoolshitRepositoryCustom {
    Optional<BoolshitLastResponse> findLastBoolshit();
}
