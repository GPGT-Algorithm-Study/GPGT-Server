package com.randps.randomdefence.domain.event.mock;

import com.randps.randomdefence.domain.event.domain.EventPoint;
import com.randps.randomdefence.domain.event.dto.EventPointDto;
import com.randps.randomdefence.domain.event.service.port.EventPointRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeEventPointRepository implements EventPointRepository {

    private final List<EventPoint> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public EventPoint save(EventPoint eventPoint) {
        if (eventPoint.getId() == null || eventPoint.getId() == 0L) {
            autoIncreasingCount++;
            EventPoint newEventPoint = EventPoint.builder().id(autoIncreasingCount).eventName(eventPoint.getEventName())
                    .description(eventPoint.getDescription()).startTime(eventPoint.getStartTime())
                    .endTime(eventPoint.getEndTime()).percentage(eventPoint.getPercentage()).build();
            data.add(newEventPoint);
            return newEventPoint;
        } else {
            data.removeIf(item -> item.getId().equals(eventPoint.getId()));
            EventPoint newEventPoint = EventPoint.builder().id(eventPoint.getId()).eventName(eventPoint.getEventName())
                    .description(eventPoint.getDescription()).startTime(eventPoint.getStartTime())
                    .endTime(eventPoint.getEndTime()).percentage(eventPoint.getPercentage()).build();
            data.add(newEventPoint);
            return newEventPoint;
        }
    }

    @Override
    public void delete(EventPoint eventPoint) {
        data.removeIf(item -> item.getId().equals(eventPoint.getId()));
    }

    @Override
    public Optional<EventPoint> findById(Long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public List<EventPointDto> findAllByDto() {
        return data.stream().sorted(Comparator.comparing(EventPoint::getStartTime))
                .map(item -> EventPointDto.builder().id(item.getId()).eventName(item.getEventName())
                        .description(item.getDescription()).startTime(item.getStartTime()).endTime(item.getEndTime())
                        .percentage(item.getPercentage()).createdDate(item.getCreatedDate())
                        .modifiedDate(item.getModifiedDate()).build()).collect(Collectors.toList());
    }

    @Override
    public List<EventPoint> findAllValidEvent() {
        LocalDateTime now = LocalDateTime.now();
        return data.stream().filter(item -> item.getStartTime().isBefore(now) && item.getEndTime().isAfter(now))
                .sorted(Comparator.comparing(EventPoint::getStartTime)).collect(Collectors.toList());
    }

    @Override
    public List<EventPointDto> findAllValidEventByDto() {
        LocalDateTime now = LocalDateTime.now();
        return data.stream().filter(item -> item.getStartTime().isBefore(now) && item.getEndTime().isAfter(now))
                .sorted(Comparator.comparing(EventPoint::getStartTime))
                .map(item -> EventPointDto.builder().id(item.getId()).eventName(item.getEventName())
                        .description(item.getDescription()).startTime(item.getStartTime()).endTime(item.getEndTime())
                        .percentage(item.getPercentage()).createdDate(item.getCreatedDate())
                        .modifiedDate(item.getModifiedDate()).build()).collect(Collectors.toList());
    }
}
