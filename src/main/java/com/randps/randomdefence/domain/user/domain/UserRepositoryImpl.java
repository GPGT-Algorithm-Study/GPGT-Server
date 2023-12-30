package com.randps.randomdefence.domain.user.domain;

import static com.randps.randomdefence.domain.user.domain.QUser.user;
import static com.randps.randomdefence.domain.user.domain.QUserRandomStreak.userRandomStreak;
import static com.randps.randomdefence.global.jwt.domain.QRefreshToken.refreshToken1;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.statistics.dto.UserIsTodaySolvedDto;
import com.randps.randomdefence.domain.statistics.dto.UserWarningBarDto;
import com.randps.randomdefence.domain.statistics.dto.YesterdayUnsolvedUserDto;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import com.randps.randomdefence.domain.user.dto.UserLastLoginLogDto;
import com.randps.randomdefence.domain.user.dto.UserMentionDto;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /*
     * 모든 유저의 랜덤스트릭 정보를 포함해서 조회하고 반환한다.
     */
    @Override
    public List<UserInfoResponse> findAllUserResponse() {
        // 유저와 유저 랜덤 스트릭을 bojHandle로 조인해서 Tuple로 가져온다.
        List<UserInfoResponse> result = queryFactory
                .select(Projections.fields(
                        UserInfoResponse.class,
                        user.bojHandle,
                        user.notionId,
                        user.manager,
                        user.warning,
                        user.profileImg,
                        user.emoji,
                        user.tier,
                        user.totalSolved,
                        user.currentStreak,
                        user.currentRandomStreak,
                        user.team,
                        user.point,
                        user.isTodaySolved,
                        user.isTodayRandomSolved,
                        user.todaySolvedProblemCount,
                        userRandomStreak.maxRandomStreak
                        ))
                .from(user)
                .join(userRandomStreak).on(user.bojHandle.eq(userRandomStreak.bojHandle))
                .fetch();

        return result;
    }

    /*
     * 모든 유저의 경고 그래프 바 DTO를 조회한다.
     */
    @Override
    public List<UserWarningBarDto> findAllWarningBarDto() {
        List<UserWarningBarDto> result = queryFactory
                .select(Projections.fields(
                        UserWarningBarDto.class,
                        user.notionId,
                        user.emoji,
                        user.warning
                ))
                .from(user)
                .fetch();

        return result;
    }

    /*
     * 모든 유저의 오늘 문제 풀었는지 여부 DTO를 조회한다.
     */
    @Override
    public List<UserIsTodaySolvedDto> findAllUserIsTodaySolvedDto() {
        List<UserIsTodaySolvedDto> result = queryFactory
                .select(Projections.fields(
                        UserIsTodaySolvedDto.class,
                        user.notionId,
                        user.emoji,
                        user.isTodaySolved
                ))
                .from(user)
                .fetch();

        return result;
    }

    /*
     * 어제 문제를 풀지 않은 모든 유저를 DTO로 조회한다.
     */
    @Override
    public List<YesterdayUnsolvedUserDto> findAllYesterdayUnsolvedUserDto() {
        List<YesterdayUnsolvedUserDto> result = queryFactory
                .select(Projections.fields(
                        YesterdayUnsolvedUserDto.class,
                        user.bojHandle,
                        user.notionId,
                        user.profileImg,
                        user.emoji
                ))
                .from(user)
                .where(user.isYesterdaySolved.eq(false))
                .fetch();

        return result;
    }

    /*
     * 모든 유저의 마지막 로그인 기록을 DTO로 조회한다.
     */
    @Override
    public List<UserLastLoginLogDto> findAllLastLoginDto() {
        List<UserLastLoginLogDto> result = queryFactory
                .select(Projections.fields(
                        UserLastLoginLogDto.class,
                        user.bojHandle,
                        user.notionId,
                        user.emoji,
                        user.profileImg,
                        refreshToken1.modifiedDate.as("lastLoginDate")
                        ))
                .from(user)
                .leftJoin(refreshToken1).on(user.bojHandle.eq(refreshToken1.bojHandle))
                .orderBy(refreshToken1.modifiedDate.desc())
                .fetch();

        return result;
    }

    /*
     * Mention을 위해 모든 User의 Dto를 반환한다.
     */
    @Override
    public List<UserMentionDto> findAllUserMentionDto() {
        List<UserMentionDto> result = queryFactory
                .select(Projections.fields(
                        UserMentionDto.class,
                        user.notionId
                ))
                .from(user)
                .orderBy(user.notionId.asc())
                .fetch();

        return result;
    }

}
