package com.randps.randomdefence.domain.board.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Optional<Board> findById(Long id);
}
