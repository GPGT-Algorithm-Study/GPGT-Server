package com.randps.randomdefence.domain.event.service;

import com.randps.randomdefence.domain.event.dto.EventPointDto;
import com.randps.randomdefence.domain.event.service.port.EventPointRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventPointSearchService {

    private final EventPointRepository eventPointRepository;

    /**
     * 모든 보너스 포인트 이벤트 조회
     */
    public List<EventPointDto> findAllEventPoint() {
        return eventPointRepository.findAllByDto();
    }

    /**
     * 현재 적용중인 모든 보너스 포인트 이벤트 조회
     */
    public List<EventPointDto> findAllValidEventPoint() {
        return eventPointRepository.findAllValidEventByDto();
    }
}
