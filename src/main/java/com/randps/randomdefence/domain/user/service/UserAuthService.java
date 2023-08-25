package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.dto.LoginFailureResponse;
import com.randps.randomdefence.domain.user.dto.LoginRequest;
import com.randps.randomdefence.domain.user.dto.LoginSuccessResponse;
import com.randps.randomdefence.global.jwt.JwtRefreshUtil;
import com.randps.randomdefence.global.jwt.domain.RefreshToken;
import com.randps.randomdefence.global.jwt.domain.RefreshTokenRepository;
import com.randps.randomdefence.global.jwt.dto.GlobalJwtResDto;
import com.randps.randomdefence.global.jwt.dto.TokenDto;
import com.randps.randomdefence.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
