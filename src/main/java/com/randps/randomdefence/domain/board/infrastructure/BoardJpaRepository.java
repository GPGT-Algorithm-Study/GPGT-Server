package com.randps.randomdefence.domain.board.infrastructure;

import com.randps.randomdefence.domain.board.domain.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    Optional<Board> findById(Long id);

    List<Board> findAllByBojHandle(String bojHandle);

}
