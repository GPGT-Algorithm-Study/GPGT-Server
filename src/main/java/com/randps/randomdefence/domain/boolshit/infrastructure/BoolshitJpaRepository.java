package com.randps.randomdefence.domain.boolshit.infrastructure;

import com.randps.randomdefence.domain.boolshit.domain.Boolshit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoolshitJpaRepository extends JpaRepository<Boolshit, Long> {
}
