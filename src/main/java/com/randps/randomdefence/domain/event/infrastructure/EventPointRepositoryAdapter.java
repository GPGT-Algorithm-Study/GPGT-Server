package com.randps.randomdefence.domain.event.infrastructure;

import com.randps.randomdefence.domain.event.domain.EventPoint;
import com.randps.randomdefence.domain.event.dto.EventPointDto;
import com.randps.randomdefence.domain.event.service.port.EventPointRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventPointRepositoryAdapter implements EventPointRepository {

    private final EventPointJpaRepository eventPointJpaRepository;

    private final EventPointRepositoryCustom eventPointRepositoryCustom;

    @Override
    public EventPoint save(EventPoint eventPoint) {
        return eventPointJpaRepository.save(eventPoint);
    }

    @Override
    public void delete(EventPoint eventPoint) {
        eventPointJpaRepository.delete(eventPoint);
    }

    @Override
    public Optional<EventPoint> findById(Long id) {
        return eventPointJpaRepository.findById(id);
    }

    @Override
    public List<EventPointDto> findAllByDto() {
        return eventPointRepositoryCustom.findAllByDto();
    }

    @Override
    public List<EventPoint> findAllValidEvent() {
        return eventPointRepositoryCustom.findAllValidEvent();
    }

    @Override
    public List<EventPointDto> findAllValidEventByDto() {
        return eventPointRepositoryCustom.findAllValidEventByDto();
    }
}
