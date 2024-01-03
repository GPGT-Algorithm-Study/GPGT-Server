package com.randps.randomdefence.domain.event.infrastructure;

import com.randps.randomdefence.domain.event.domain.EventPoint;
import com.randps.randomdefence.domain.event.dto.EventPointDto;
import java.util.List;

public interface EventPointRepositoryCustom {
    // 모든 이벤트 EventPointDto List로 조회
    List<EventPointDto> findAllByDto();

    // 현재 적용중인 모든 EventPoint List로 조회
    List<EventPoint> findAllValidEvent();

    // 현재 적용중인 모든 EventPointDto List로 조회
    List<EventPointDto> findAllValidEventByDto();
}
