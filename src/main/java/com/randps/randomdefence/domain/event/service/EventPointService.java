package com.randps.randomdefence.domain.event.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.randps.randomdefence.domain.event.domain.EventPoint;
import com.randps.randomdefence.domain.event.domain.EventPointRepository;
import com.randps.randomdefence.domain.event.dto.EventPointPublishRequest;
import com.randps.randomdefence.domain.event.dto.EventPointUpdateRequest;
import com.randps.randomdefence.domain.log.service.PointLogSaveService;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventPointService {

    private final EventPointRepository eventPointRepository;

    private final PointLogSaveService pointLogSaveService;

    private final UserRepository userRepository;

    /**
     * 보너스 포인트 이벤트 생성
     */
    @Transactional
    public EventPoint publishEventPoint(EventPointPublishRequest eventPointPublishRequest) {
        EventPoint event = EventPoint.builder()
                .eventName(eventPointPublishRequest.getEventName())
                .description(eventPointPublishRequest.getDescription())
                .startTime(eventPointPublishRequest.getStartTime())
                .endTime(eventPointPublishRequest.getEndTime())
                .percentage(eventPointPublishRequest.getPercentage())
                .build();

        eventPointRepository.save(event);

        return event;
    }

    /**
     * 보너스 포인트 이벤트 수정
     */
    @Transactional
    public EventPoint updateEventPoint(EventPointUpdateRequest eventPointUpdateRequest) {
        EventPoint event = eventPointRepository.findById(eventPointUpdateRequest.getId()).orElseThrow(() -> new NotFoundException("존재하지 않는 이벤트입니다."));

        event.update(eventPointUpdateRequest.getEventName(),
                eventPointUpdateRequest.getDescription(),
                eventPointUpdateRequest.getStartTime(),
                eventPointUpdateRequest.getEndTime(),
                eventPointUpdateRequest.getPercentage());

        eventPointRepository.save(event);

        return event;
    }

    /**
     * 보너스 포인트 이벤트 삭제
     */
    @Transactional
    public void deleteEventPoint(Long eventPointId) {
        EventPoint event = eventPointRepository.findById(eventPointId).orElseThrow(() -> new NotFoundException("존재하지 않는 이벤트입니다."));

        eventPointRepository.delete(event);
    }

    /**
     * Valid한 이벤트가 존재한다면 보너스 포인트 이벤트 적용
     */
    @Transactional
    public boolean applyEventPoint(String bojHandle, Integer changedValue) {
        List<EventPoint> validEvents = eventPointRepository.findAllValidEvent();
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (validEvents.isEmpty()) return false;

        for (EventPoint event : validEvents) {
            Integer bonusPoints = (int) Math.round((double)changedValue * event.getPercentage());
            String description = bonusPoints + " extra points are earned by event \'" + event.getEventName() + "\' 🔥️🔥🔥";
            Boolean state = true;

            if (bonusPoints == 0) continue;
            user.increasePoint(bonusPoints);
            pointLogSaveService.savePointLog(bojHandle, bonusPoints, description, state);
        }
        userRepository.save(user);

        return true;
    }
}
