package com.randps.randomdefence.domain.image.infrastructure;

import static com.randps.randomdefence.domain.image.domain.QImage.image;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ImageRepositoryImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ImageRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 기준시간보다 이전이면서 게시글과 연결되지 않은 이미지들을 조회한다.
     */
    @Override
    public List<Long> findAllByCriterion(LocalDateTime criterion) {

        List<Long> result = queryFactory
                .select(image.id)
                .from(image)
                .where(image.createdDate.before(criterion).and(image.state.eq(false)))
                .fetch();

        return result;
    }
}
