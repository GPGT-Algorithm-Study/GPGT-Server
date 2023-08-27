package com.randps.randomdefence.domain.user.controller;

import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.team.service.TeamSettingService;
import com.randps.randomdefence.domain.user.dto.authDto.*;
import com.randps.randomdefence.domain.user.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CertificateExpiredException;

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
    public ParseDto parseBojJHandle(@Param("token") String token) throws CertificateExpiredException {
        return userAuthService.getBojHandleByJWT(token);
    }

//    /*
//     * 로그인 : 요청 body에 json형식으로 다음과 같은 데이터를 넘겨주면 된다.
//     * {
//     *      "bojHandle" : "백준핸들",
//     *      "password" : "비밀번호"
//     * }
//     */
//    @PostMapping("/login")
//    public Object login(@RequestBody LoginRequest loginRequest) {
//        return userAuthService.login(loginRequest.getBojHandle(), loginRequest.getPassword());
//    }

//    /*
//     * JWT 인증 테스트용 엔드포인트
//     */
//    @GetMapping("/info")
//    public String info(@AuthenticationPrincipal PrincipalDetails principalDetails, Authentication authentication) {
//        System.out.println("PrincipalDetails " + principalDetails);
//        System.out.println("authentication " + authentication);
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("PrincipalDetails ");
//        sb.append(principalDetails);
//        sb.append("\n\n");
//        sb.append("authentication ");
//        sb.append(authentication);
//
//        return sb.toString();
//    }

}
