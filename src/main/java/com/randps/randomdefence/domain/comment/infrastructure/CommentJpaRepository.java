package com.randps.randomdefence.domain.comment.infrastructure;

import com.randps.randomdefence.domain.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBojHandle(String bojHandle);

}
