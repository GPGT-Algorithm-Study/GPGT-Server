package com.randps.randomdefence.domain.user.controller;

import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.team.service.TeamSettingService;
import com.randps.randomdefence.domain.user.dto.authDto.ChangePasswordRequest;
import com.randps.randomdefence.domain.user.dto.authDto.ChangePasswordResponse;
import com.randps.randomdefence.domain.user.dto.authDto.LoginRequest;
import com.randps.randomdefence.domain.user.dto.authDto.LoginSuccessResponse;
import com.randps.randomdefence.domain.user.dto.authDto.LogoutRequest;
import com.randps.randomdefence.domain.user.dto.authDto.LogoutResponse;
import com.randps.randomdefence.domain.user.dto.authDto.ParseDto;
import com.randps.randomdefence.domain.user.dto.authDto.RefreshDto;
import com.randps.randomdefence.domain.user.service.UserAuthService;
import com.randps.randomdefence.domain.user.service.UserGrassService;
import com.randps.randomdefence.domain.user.service.UserInfoService;
import com.randps.randomdefence.domain.user.service.UserRandomStreakService;
import com.randps.randomdefence.domain.user.service.UserService;
import com.randps.randomdefence.domain.user.service.UserSolvedProblemService;
import java.security.cert.CertificateExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;

    private final UserService userService;

    private final UserInfoService userInfoService;

    private final UserSolvedProblemService userSolvedProblemService;

    private final UserRandomStreakService userRandomStreakService;

    private final UserGrassService userGrassService;

    private final TeamSettingService teamSettingService;

    private final TeamService teamService;

    /*
     * 로그인 : 요청 body에 json형식으로 다음과 같은 데이터를 넘겨주면 된다.
     * {
     *      "bojHandle" : "백준핸들",
     *      "password" : "비밀번호"
     * }
     */
    @PostMapping("/login")
    public LoginSuccessResponse login(@RequestBody LoginRequest loginRequest) {
        return userAuthService.login(loginRequest);
    }

    /*
     * 로그아웃 : 요청 body에 json형식으로 다음과 같은 데이터를 넘겨주면 된다.
     * 헤더에 유저 본인의 유효한 "Refresh_Token"이 들어있어야 한다.
     * {
     *      "bojHandle" : "백준핸들"
     * }
     */
    @PostMapping("/logout")
    public LogoutResponse logout(@RequestHeader("Refresh_Token") String refresh, @RequestBody LogoutRequest logoutRequest) {
        return userAuthService.logout(logoutRequest, refresh);
    }

    /*
     * 비밀변호 변경 : 요청 body에 json형식으로 다음과 같은 데이터를 넘겨주면 된다.
     * 헤더에 유저 본인의 유효한 "Refresh_Token"이 들어있어야 한다.
     * {
     *      "bojHandle" : "백준핸들",
     *      "oldPassword" : "이전 비밀번호",
     *      "newPassword" : "바꿀 비밀번호"
     * }
     * 비밀번호 변경에 성공하면 기존 로그인은 로그아웃 처리된다.
     */
    @PostMapping("/pwchange")
    public ChangePasswordResponse changePassword(@RequestHeader("Refresh_Token") String refresh, @RequestBody ChangePasswordRequest changePasswordRequest) {
        return userAuthService.change(changePasswordRequest, refresh);
    }

    /*
     * Access_Token 재발급 엔드포인트
     */
    @GetMapping("/refresh")
    public RefreshDto refreshAccessToken(@RequestHeader("Refresh_Token") String refresh) throws CertificateExpiredException {
        return userAuthService.refreshAccessToken(refresh);
    }

    /*
     * 토큰으로 bojHandle 반환
     */
    @GetMapping("/parse/boj")
    public ParseDto parseBojJHandle(@RequestHeader("Refresh_Token") String token) throws CertificateExpiredException {
        return userAuthService.getBojHandleByJWT(token);
    }

}
