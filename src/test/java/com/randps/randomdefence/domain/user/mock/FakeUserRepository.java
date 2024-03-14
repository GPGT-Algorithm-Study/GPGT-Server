package com.randps.randomdefence.domain.user.mock;

import com.randps.randomdefence.domain.statistics.dto.UserIsTodaySolvedDto;
import com.randps.randomdefence.domain.statistics.dto.UserWarningBarDto;
import com.randps.randomdefence.domain.statistics.dto.YesterdayUnsolvedUserDto;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import com.randps.randomdefence.domain.user.dto.UserLastLoginLogDto;
import com.randps.randomdefence.domain.user.dto.UserMentionDto;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeUserRepository implements UserRepository {

    private final List<User> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public Optional<User> findByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).findAny();
    }

    @Override
    public List<User> findAllByTeam(Integer team) {
        return data.stream().filter(item -> item.getTeam().equals(team)).collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return data;
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId() == 0) {
            autoIncreasingCount++;
            User newUser = User.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(user.getBojHandle())
                    .password(user.getPassword())
                    .roles(user.getRoles())
                    .notionId(user.getNotionId())
                    .manager(user.getManager())
                    .warning(user.getWarning())
                    .profileImg(user.getProfileImg())
                    .emoji(user.getEmoji())
                    .tier(user.getTier())
                    .totalSolved(user.getTotalSolved())
                    .currentStreak(user.getCurrentStreak())
                    .currentRandomStreak(user.getCurrentRandomStreak())
                    .team(user.getTeam())
                    .point(user.getPoint())
                    .isTodaySolved(user.getIsTodaySolved())
                    .isYesterdaySolved(user.getIsYesterdaySolved())
                    .isTodayRandomSolved(user.getIsTodayRandomSolved())
                    .todaySolvedProblemCount(user.getTodaySolvedProblemCount())
                    .build();
            data.add(newUser);
            return newUser;
        } else {
            data.removeIf(item -> item.getId().equals(user.getId()));
            User newUser = User.builder()
                    .id(user.getId())
                    .bojHandle(user.getBojHandle())
                    .password(user.getPassword())
                    .roles(user.getRoles())
                    .notionId(user.getNotionId())
                    .manager(user.getManager())
                    .warning(user.getWarning())
                    .profileImg(user.getProfileImg())
                    .emoji(user.getEmoji())
                    .tier(user.getTier())
                    .totalSolved(user.getTotalSolved())
                    .currentStreak(user.getCurrentStreak())
                    .currentRandomStreak(user.getCurrentRandomStreak())
                    .team(user.getTeam())
                    .point(user.getPoint())
                    .isTodaySolved(user.getIsTodaySolved())
                    .isYesterdaySolved(user.getIsYesterdaySolved())
                    .isTodayRandomSolved(user.getIsTodayRandomSolved())
                    .todaySolvedProblemCount(user.getTodaySolvedProblemCount())
                    .build();
            data.add(newUser);
            return newUser;
        }
    }

    @Override
    public void delete(User user) {
        data.removeIf(item -> item.getId().equals(user.getId()));
    }

    @Override
    public List<UserInfoResponse> findAllUserResponse() {
        return data.stream().map(UserInfoResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<UserWarningBarDto> findAllWarningBarDto() {
        return data.stream().map(UserWarningBarDto::new).collect(Collectors.toList());
    }

    @Override
    public List<UserIsTodaySolvedDto> findAllUserIsTodaySolvedDto() {
        return data.stream().map(UserIsTodaySolvedDto::new).collect(Collectors.toList());
    }

    @Override
    public List<YesterdayUnsolvedUserDto> findAllYesterdayUnsolvedUserDto() {
        return data.stream().map(YesterdayUnsolvedUserDto::new).collect(Collectors.toList());
    }

    @Override
    public List<UserLastLoginLogDto> findAllLastLoginDto() {
        return data.stream().map(UserLastLoginLogDto::new).collect(Collectors.toList());
    }

    @Override
    public List<UserMentionDto> findAllUserMentionDto() {
        return data.stream().map(UserMentionDto::new).collect(Collectors.toList());
    }

}
