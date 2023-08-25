package com.randps.randomdefence.domain.user.controller;

import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.team.service.TeamSettingService;
import com.randps.randomdefence.domain.user.dto.LoginRequest;
import com.randps.randomdefence.domain.user.dto.LoginSuccessResponse;
import com.randps.randomdefence.domain.user.dto.PrincipalDetails;
import com.randps.randomdefence.domain.user.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
