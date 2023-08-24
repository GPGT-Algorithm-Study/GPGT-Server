package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.dto.LoginFailureResponse;
import com.randps.randomdefence.domain.user.dto.LoginSuccessResponse;
import com.randps.randomdefence.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserAuthService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Transactional
    public Object login(String bojHandle, String password) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new UsernameNotFoundException("잘못된 아이디입니다."));

        // 비밀번호 일치 여부 확인
        if(passwordEncoder.matches(password, user.getPassword())){
            return LoginSuccessResponse.builder()
                    .bojHandle(bojHandle)
                    .password(password)
                    .jwtToken(jwtProvider.generateJwtToken(user.getId(), user.getBojHandle(), user.getNotionId()))
                    .build();
        }

        // 비밀번호 매칭 실패
        return LoginFailureResponse.builder().message("비밀번호가 일치하지 않습니다.").build();
    }

}
