package com.randps.randomdefence.domain.event.infrastructure;

import com.randps.randomdefence.domain.event.domain.EventPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPointJpaRepository extends JpaRepository<EventPoint, Long> {
}
