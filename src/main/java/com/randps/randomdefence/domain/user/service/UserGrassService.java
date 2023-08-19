package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.*;
import com.randps.randomdefence.domain.user.dto.UserGrassDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserGrassService {

    private final UserGrassRepository userGrassRepository;

    private final UserRepository userRepository;

    private final UserRandomStreakRepository userRandomStreakRepository;

    /*
     * 특정 유저의 오늘의 잔디를 생성한다.
     */
    @Transactional
    public void makeTodayGrass(UserRandomStreak userRandomStreak) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() < 6) now = now.minusDays(1);
        String today = now.toString().substring(0,10);
        Optional<UserGrass> isExistTodayUserGrass = userGrassRepository.findByUserRandomStreakAndDate(userRandomStreak, today);

        if (isExistTodayUserGrass.isPresent()) return;

        UserGrass todayUserGrass = UserGrass.builder()
                .problemId(userRandomStreak.getTodayRandomProblemId())
                .date(today)
                .grassInfo(false)
                .userRandomStreak(userRandomStreak)
                .build();
        userGrassRepository.save(todayUserGrass);
    }

    /*
     * 특정 유저의 전날의 잔디를 생성한다.
     */
    @Transactional
    public void makeYesterdayGrass(UserRandomStreak userRandomStreak) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() >= 6) now = now.minusDays(1);
        if (now.getHour() < 6) now = now.minusDays(2);
        String yesterday = now.toString().substring(0,10);

        UserGrass todayUserGrass = UserGrass.builder()
                .problemId(userRandomStreak.getTodayRandomProblemId())
                .date(yesterday)
                .grassInfo(false)
                .userRandomStreak(userRandomStreak)
                .build();
        userGrassRepository.save(todayUserGrass);
    }

    /*
     * 모든 유저의 오늘의 잔디를 생성한다.
     */
    @Transactional
    public void makeTodayGrassAll() {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() < 6) now = now.minusDays(1);
        String today = now.toString().substring(0,10);
        List<User> users = userRepository.findAll();

        for (User user : users) {
            UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(user.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));;
            Optional<UserGrass> isExistTodayUserGrass = userGrassRepository.findByUserRandomStreakAndDate(userRandomStreak, today);

            if (isExistTodayUserGrass.isPresent()) return;

            UserGrass todayUserGrass = UserGrass.builder()
                    .problemId(userRandomStreak.getTodayRandomProblemId())
                    .date(today)
                    .grassInfo(false)
                    .userRandomStreak(userRandomStreak)
                    .build();
            userGrassRepository.save(todayUserGrass);
        }
    }

    /*
     * 인자로 받은 스트릭의 문제를 해결한 날의 모든 잔디 Dto 리스트를 반환한다.
     */
    @Transactional
    public List<UserGrassDto> findUserGrassList(UserRandomStreak userRandomStreak) {
        List<UserGrass> userGrasses =  userGrassRepository.findAllByUserRandomStreakAndGrassInfo(userRandomStreak, true);
        List<UserGrassDto> userGrassDtos = new ArrayList<>();

        for (UserGrass userGrass : userGrasses) {
            userGrassDtos.add(userGrass.toDto());
        }

        return userGrassDtos;
    }

    /*
     * 오늘의 잔디를 반환한다.
     */
    @Transactional
    public UserGrass findTodayUserGrass(UserRandomStreak userRandomStreak) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() < 6) now = now.minusDays(1);
        String today = now.toString().substring(0,10);
        System.out.println(today);

        return userGrassRepository.findByUserRandomStreakAndDate(userRandomStreak, today).orElseThrow(() -> new IllegalArgumentException("아직 존재하지 않는 스트릭입니다."));
    }

    /*
     * 전날의 잔디를 반환한다.
     */
    @Transactional
    public UserGrass findYesterdayUserGrass(UserRandomStreak userRandomStreak) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() >= 6) now = now.minusDays(1);
        if (now.getHour() < 6) now = now.minusDays(2);
        String yesterday = now.toString().substring(0,10);

        return userGrassRepository.findByUserRandomStreakAndDate(userRandomStreak, yesterday).orElseThrow(() -> new IllegalArgumentException("아직 존재하지 않는 스트릭입니다."));
    }
}
