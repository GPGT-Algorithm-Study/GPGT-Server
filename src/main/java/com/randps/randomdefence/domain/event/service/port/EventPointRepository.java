package com.randps.randomdefence.domain.event.service.port;

import com.randps.randomdefence.domain.event.domain.EventPoint;
import com.randps.randomdefence.domain.event.dto.EventPointDto;
import java.util.List;
import java.util.Optional;

public interface EventPointRepository {

    EventPoint save(EventPoint eventPoint);

    void delete(EventPoint eventPoint);

    Optional<EventPoint> findById(Long id);


    // 모든 이벤트 EventPointDto List로 조회
    List<EventPointDto> findAllByDto();

    // 현재 적용중인 모든 EventPoint List로 조회
    List<EventPoint> findAllValidEvent();

    // 현재 적용중인 모든 EventPointDto List로 조회
    List<EventPointDto> findAllValidEventByDto();

}
