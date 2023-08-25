package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.dto.authDto.*;
import com.randps.randomdefence.global.jwt.JwtRefreshUtil;
import com.randps.randomdefence.global.jwt.domain.RefreshToken;
import com.randps.randomdefence.global.jwt.domain.RefreshTokenRepository;
import com.randps.randomdefence.global.jwt.dto.TokenDto;
import com.randps.randomdefence.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserAuthService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final JwtRefreshUtil jwtUtil;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public LoginSuccessResponse login(LoginRequest loginReqDto) {

        // 아이디 검사
        User user = userRepository.findByBojHandle(loginReqDto.getBojHandle()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        // 비밀번호 검사
        if(!passwordEncoder.matches(loginReqDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        // 아이디 정보로 Token생성
        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getBojHandle());

        // Refresh토큰 있는지 확인
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByBojHandle(loginReqDto.getBojHandle());

        // 있다면 새토큰 발급후 업데이트
        // 없다면 새로 만들고 디비 저장
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getBojHandle());
            refreshTokenRepository.save(newToken);
        }

        return LoginSuccessResponse.builder().bojHandle(loginReqDto.getBojHandle()).jwt(tokenDto).build();

    }

    @Transactional
    public LogoutResponse logout(LogoutRequest logoutReq, String refresh) {

        // 아이디 검사
        User user = userRepository.findByBojHandle(logoutReq.getBojHandle()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        // Refresh토큰으로 토큰과 사용자 일치 검사
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByBojHandle(logoutReq.getBojHandle());
        if(!refreshToken.isPresent()) {
            // 목표 유저의 리프레쉬 토큰이 존재하지 않는다면 유저가 맞지 않는 것이다.
            throw new RuntimeException("Not matches User");
        } else if(!refreshToken.get().getRefreshToken().equals(refresh)) {
            // 유저의 리프레쉬 토큰과 일치하지 않는다면 유저가 맞지 않거나 만료된 것이다.
            return LogoutResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.toString())
                    .message("RefreshToken Expired")
                    .build();
        } else {
            // 유저의 요청과 리프레쉬 토큰이 일치한다면 DB에서 사용자의 리프레쉬 토큰을 삭제한다.
            refreshTokenRepository.delete(refreshToken.get());
            return LogoutResponse.builder()
                    .code(HttpStatus.OK.toString())
                    .message("로그아웃에 성공했습니다.")
                    .build();
        }
    }

    @Transactional
    public ChangePasswordResponse change(ChangePasswordRequest changePasswordReq, String refresh) {

        // 아이디 검사
        User user = userRepository.findByBojHandle(changePasswordReq.getBojHandle()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        // Refresh토큰으로 토큰과 사용자 일치 검사
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByBojHandle(changePasswordReq.getBojHandle());
        if(!refreshToken.isPresent()) {
            // 목표 유저의 리프레쉬 토큰이 존재하지 않는다면 유저가 맞지 않는 것이다.
            throw new RuntimeException("Not matches User");
        } else if(!refreshToken.get().getRefreshToken().equals(refresh)) {
            // 유저의 리프레쉬 토큰과 일치하지 않는다면 유저가 맞지 않거나 만료된 것이다.
            return ChangePasswordResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.toString())
                    .message("RefreshToken Expired")
                    .build();
        } else {
            // 로그인한 유저본인임이 검증됐다면 비밀번호를 검증한다.
            // 비밀번호 검사
            if(!passwordEncoder.matches(changePasswordReq.getOldPassword(), user.getPassword())) {
                throw new RuntimeException("Not matches Password");
            }

            // 유저의 이전 비밀번호와 변경할 비밀번호가 같다면 바꾸지 못하게 한다.
            if(changePasswordReq.getOldPassword().equals(changePasswordReq.getNewPassword())) {
                throw new RuntimeException("Same Password");
            }

            // 유저의 요청과 리프레쉬 토큰이 일치하고 이전 비밀번호가 일치한다면 DB에서 사용자의 리프레쉬 토큰을 삭제한다.
            refreshTokenRepository.delete(refreshToken.get());

            // 유저의 요청과 리프레쉬 토큰이 일치하고 이전 비밀번호가 일치한다면 유저의 비밀번호를 변경해서 저장한다.
            user.changePassword(passwordEncoder.encode(changePasswordReq.getNewPassword()));
            userRepository.save(user);

            // 결과 반환
            return ChangePasswordResponse.builder()
                    .code(HttpStatus.OK.toString())
                    .message("비밀번호 변경에 성공했습니다.")
                    .build();
        }
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtRefreshUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtRefreshUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    // 기존 로그인 방법
//    @Transactional
//    public Object login(String bojHandle, String password) {
//        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new UsernameNotFoundException("잘못된 아이디입니다."));
//
//        // 비밀번호 일치 여부 확인
//        if(passwordEncoder.matches(password, user.getPassword())){
//            return LoginSuccessResponse.builder()
//                    .bojHandle(bojHandle)
//                    .password(password)
//                    // 첫번째는 엑세스토큰이고, 두번째는 리프레시토큰
//                    .jwt(new TokenDto(jwtProvider.generateJwtToken(user.getId(), user.getBojHandle(), user.getNotionId()), jwtProvider.generateJwtToken(user.getId(), user.getBojHandle(), user.getNotionId())))
//                    .build();
//        }
//
//        // 비밀번호 매칭 실패
//        return LoginFailureResponse.builder().message("비밀번호가 일치하지 않습니다.").build();
//    }

}
