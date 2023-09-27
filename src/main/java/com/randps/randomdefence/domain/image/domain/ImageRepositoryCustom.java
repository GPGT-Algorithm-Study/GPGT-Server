package com.randps.randomdefence.domain.image.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface ImageRepositoryCustom {

    /**
     * 배치잡에서 삭제하기 위한 이미지Id를 모두 조회한다.
     */
    List<Long> findAllByCriterion(LocalDateTime criterion);
}
