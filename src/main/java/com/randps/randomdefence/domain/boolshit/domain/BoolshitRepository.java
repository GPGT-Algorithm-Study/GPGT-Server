package com.randps.randomdefence.domain.boolshit.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoolshitRepository extends JpaRepository<Boolshit, Long>, BoolshitRepositoryCustom {
}
