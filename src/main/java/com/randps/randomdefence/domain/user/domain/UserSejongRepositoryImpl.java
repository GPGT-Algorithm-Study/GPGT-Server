package com.randps.randomdefence.domain.user.domain;

import static com.randps.randomdefence.domain.user.domain.QUserSejong.userSejong;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.statistics.dto.UserRankDto;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class UserSejongRepositoryImpl implements UserSejongRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserSejongRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<UserRankDto> findAllUserRankPaging(Pageable pageable) {
        List<UserRankDto> result = queryFactory.select(Projections.fields(
                UserRankDto.class,
                userSejong.bojHandle,
                userSejong.profileImg,
                userSejong.tier,
                userSejong.totalSolved))
                .from(userSejong)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(userSejong.totalSolved.desc())
                .fetch();

        Long count = queryFactory
                .select(userSejong.count())
                .from(userSejong)
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

}
