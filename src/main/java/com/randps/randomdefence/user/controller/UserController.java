package com.randps.randomdefence.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.user.domain.User;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import com.randps.randomdefence.user.service.UserInfoService;
import com.randps.randomdefence.user.service.UserService;
import com.randps.randomdefence.userSolvedProblem.dto.SolvedProblemDto;
import com.randps.randomdefence.userSolvedProblem.service.UserSolvedProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final UserInfoService userInfoService;

    private final UserSolvedProblemService userSolvedProblemService;

    //TODO: useradd, userdel은 jwt 토큰을 헤더에 넣어야지 접근가능하게 설정
    /*
     * 유저를 DB에 추가한다.
     */
    @PostMapping("/add")
    public HttpStatus userAdd(@Param("bojHandle") String bojHandle, @Param("notionId") String notionId, @Param("manager") Long manager, @Param("emoji") String emoji) throws JsonProcessingException {
        userService.save(bojHandle, notionId, manager, emoji);
        userInfoService.crawlUserInfo(bojHandle);

        return HttpStatus.OK;
    }

    /*
     * 유저를 DB에서 삭제한다.
     */
    @DeleteMapping("/del")
    public HttpStatus userDel(@Param("bojHandle") String bojHandle) {
        userService.delete(bojHandle);

        return HttpStatus.OK;
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

}
