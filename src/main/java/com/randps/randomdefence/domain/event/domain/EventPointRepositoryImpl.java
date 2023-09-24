package com.randps.randomdefence.domain.event.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.event.dto.EventPointDto;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.randps.randomdefence.domain.event.domain.QEventPoint.eventPoint;

public class EventPointRepositoryImpl implements EventPointRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public EventPointRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<EventPointDto> findAllByDto() {
        List<EventPointDto> result = queryFactory
                .select(Projections.fields(
                        EventPointDto.class,
                        eventPoint.id,
                        eventPoint.createdDate,
                        eventPoint.modifiedDate,
                        eventPoint.eventName,
                        eventPoint.description,
                        eventPoint.startTime,
                        eventPoint.endTime,
                        eventPoint.percentage
                ))
                .from(eventPoint)
                .orderBy(eventPoint.startTime.asc())
                .fetch();

        return result;
    }

    @Override
    public List<EventPoint> findAllValidEvent() {
        LocalDateTime now = LocalDateTime.now();

        List<EventPoint> result = queryFactory
                .selectFrom(eventPoint)
                .where(eventPoint.startTime.before(now).and(eventPoint.endTime.after(now)))
                .orderBy(eventPoint.startTime.asc())
                .fetch();

        return result;
    }

    @Override
    public List<EventPointDto> findAllValidEventByDto() {
        LocalDateTime now = LocalDateTime.now();

        List<EventPointDto> result = queryFactory
                .select(Projections.fields(
                        EventPointDto.class,
                        eventPoint.id,
                        eventPoint.createdDate,
                        eventPoint.modifiedDate,
                        eventPoint.eventName,
                        eventPoint.description,
                        eventPoint.startTime,
                        eventPoint.endTime,
                        eventPoint.percentage
                ))
                .from(eventPoint)
                .where(eventPoint.startTime.before(now).and(eventPoint.endTime.after(now)))
                .orderBy(eventPoint.startTime.asc())
                .fetch();

        return result;
    }
}
