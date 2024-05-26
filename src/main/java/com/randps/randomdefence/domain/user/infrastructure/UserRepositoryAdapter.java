package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.statistics.dto.UserIsTodaySolvedDto;
import com.randps.randomdefence.domain.statistics.dto.UserWarningBarDto;
import com.randps.randomdefence.domain.statistics.dto.YesterdayUnsolvedUserDto;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import com.randps.randomdefence.domain.user.dto.UserLastLoginLogDto;
import com.randps.randomdefence.domain.user.dto.UserMentionDto;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    private final UserRepositoryImpl userRepositoryImpl;

    @Override
    public Optional<User> findByBojHandle(String bojHandle) {
        return userJpaRepository.findByBojHandle(bojHandle);
    }

    @Override
    public List<User> findAllByTeam(Integer team) {
        return userJpaRepository.findAllByTeam(team);
    }

    @Override
    public List<User> findAllByManager(Boolean manager) {
        return userJpaRepository.findAllByManager(manager);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(user);
    }

    @Override
    public List<UserInfoResponse> findAllUserResponse() {
        return userRepositoryImpl.findAllUserResponse();
    }

    @Override
    public List<UserWarningBarDto> findAllWarningBarDto() {
        return userRepositoryImpl.findAllWarningBarDto();
    }

    @Override
    public List<UserIsTodaySolvedDto> findAllUserIsTodaySolvedDto() {
        return userRepositoryImpl.findAllUserIsTodaySolvedDto();
    }

    @Override
    public List<YesterdayUnsolvedUserDto> findAllYesterdayUnsolvedUserDto() {
        return userRepositoryImpl.findAllYesterdayUnsolvedUserDto();
    }

    @Override
    public List<UserLastLoginLogDto> findAllLastLoginDto() {
        return userRepositoryImpl.findAllLastLoginDto();
    }

    @Override
    public List<UserMentionDto> findAllUserMentionDto() {
        return userRepositoryImpl.findAllUserMentionDto();
    }

}
