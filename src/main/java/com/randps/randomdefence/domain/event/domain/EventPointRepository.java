package com.randps.randomdefence.domain.event.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPointRepository extends JpaRepository<EventPoint, Long>, EventPointRepositoryCustom {
}
