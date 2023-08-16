package com.randps.randomdefence.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.user.domain.UserRandomStreak;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import com.randps.randomdefence.user.dto.UserSolvedProblemPairDto;
import com.randps.randomdefence.user.service.*;
import com.randps.randomdefence.user.dto.SolvedProblemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final UserInfoService userInfoService;

    private final UserSolvedProblemService userSolvedProblemService;

    private final UserRandomStreakService userRandomStreakService;

    private final UserGrassService userGrassService;

    //TODO: useradd, userdel은 jwt 토큰을 헤더에 넣어야지 접근가능하게 설정
    /*
     * 유저를 DB에 추가한다.
     */
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> userAdd(@Param("bojHandle") String bojHandle, @Param("notionId") String notionId, @Param("manager") Long manager, @Param("emoji") String emoji) throws JsonProcessingException {
        userService.save(bojHandle, notionId, manager, emoji);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "유저를 성공적으로 생성했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 유저를 DB에서 삭제한다.
     */
    @DeleteMapping("/del")
    public ResponseEntity<Map<String, String>> userDel(@Param("bojHandle") String bojHandle) {
        userService.delete(bojHandle);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "유저를 성공적으로 삭제했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 유저의 프로필 정보를 불러온다.
     */
    @GetMapping("/info")
    public UserInfoResponse info(@Param("bojHandle") String bojHandle) {
        return userInfoService.getInfo(bojHandle);
    }

    /*
     * 모든 유저의 프로필 정보를 불러온다. (유저 정보만)
     */
    @GetMapping("/info/all")
    public List<UserInfoResponse> allInfo() {
        return userInfoService.getAllInfo();
    }

    /*
     * 모든 유저의 오늘 푼 문제 목록을 불러온다.
     */
    @GetMapping("/info/today-solved/all")
    public List<UserSolvedProblemPairDto> todaySolvedAll() {
        return userSolvedProblemService.findAllTodayUserSolvedProblemAll();
    }

    /*
     * 유저의 오늘 푼 문제 목록을 불러온다.
     */
    @GetMapping("/info/today-solved")
    public List<SolvedProblemDto> todaySolved(@Param("bojHandle") String bojHandle) {
        return userSolvedProblemService.findAllTodayUserSolvedProblem(bojHandle);
    }

    /*
     * 유저의 프로필 정보를 불러온다. (테스트용 직접 요청)
     */
    @GetMapping("/info/raw")
    public JsonNode infoRaw(@Param("bojHandle") String bojHandle) throws JsonProcessingException {
        return userInfoService.getInfoRaw(bojHandle);
    }

    /*
     * 유저의 오늘 푼 문제 목록을 불러온다. (테스트용 직접 요청)
     */
    @GetMapping("/info/today-solved/raw")
    public List<Object> todaySolvedRaw(@Param("bojHandle") String bojHandle) throws JsonProcessingException {
        return userInfoService.getTodaySolvedRaw(bojHandle);
    }

    /*
     * 모든 유저를 DB에 추가한다. (TEST BATCH)
     */
    @PostMapping("/add/all")
    public ResponseEntity<Map<String, String>> userAddAll() throws JsonProcessingException {
        List<String> bojHandles = new ArrayList<>();
        List<String> notionIds = new ArrayList<>();
        List<Boolean> managers = new ArrayList<>();
        List<String> emojis = new ArrayList<>();

        // 이름 넣기
        bojHandles.add("melonboy");
        bojHandles.add("seyeon0207");
        bojHandles.add("fin");
        bojHandles.add("seoheo");
        bojHandles.add("eogns47");
        bojHandles.add("seyjang");
        bojHandles.add("hyeonjinan096");
        bojHandles.add("asdf016182");
        bojHandles.add("jake0104");
        bojHandles.add("emforhs0315");
        bojHandles.add("taipaise");
        bojHandles.add("chltjwl22");
        bojHandles.add("choish20");
        bojHandles.add("mathpaul3");
        bojHandles.add("dkssudgkgl");
        bojHandles.add("jin20fd");
        bojHandles.add("ss001015");
        bojHandles.add("hdaisywd");
        bojHandles.add("angrypig7");
        bojHandles.add("wlgh1553");
        bojHandles.add("choidg33");

        // 노션 아이디 넣기
        notionIds.add("Minboy");
        notionIds.add("Seyeon Yang");
        notionIds.add("성민");
        notionIds.add("SY Heo");
        notionIds.add("KangManJoo");
        notionIds.add("sayyoung");
        notionIds.add("hyeon200");
        notionIds.add("klloo");
        notionIds.add("재현 주");
        notionIds.add("성훈 조");
        notionIds.add("이동현");
        notionIds.add("최서지");
        notionIds.add("최승헌");
        notionIds.add("Paul Eom");
        notionIds.add("은정 방");
        notionIds.add("성유진");
        notionIds.add("RubyTubi");
        notionIds.add("Dahee Hong");
        notionIds.add("Kihun Song");
        notionIds.add("이지호");
        notionIds.add("최다경");

        // 매니저 여부 넣기
        managers.add(true);
        managers.add(true);
        managers.add(true);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(true);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);
        managers.add(false);

        // 이모지 넣기
        emojis.add("🐧");
        emojis.add("🐇");
        emojis.add("🍟");
        emojis.add("🚀");
        emojis.add("🐱");
        emojis.add("🍀");
        emojis.add("🍡");
        emojis.add("♨️");
        emojis.add("🍷");
        emojis.add("👍");
        emojis.add("🐾");
        emojis.add("🥑");
        emojis.add("🍞");
        emojis.add("🦊");
        emojis.add("✱");
        emojis.add("💫");
        emojis.add("✏️");
        emojis.add("☃️");
        emojis.add("🐴");
        emojis.add("🐸");
        emojis.add("🍎");

        for (int i=0;i< bojHandles.size();i++) {
            userService.save(bojHandles.get(i), notionIds.get(i), managers.get(i)?1L:0L, emojis.get(i));
        }


        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", bojHandles.size() + "명의 유저 리스트를 성공적으로 생성했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
