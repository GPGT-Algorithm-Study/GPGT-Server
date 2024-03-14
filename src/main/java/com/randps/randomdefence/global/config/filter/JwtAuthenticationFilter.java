package com.randps.randomdefence.global.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.dto.authDto.PrincipalDetails;
import com.randps.randomdefence.global.jwt.component.JWTProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 인증을 위한 필터
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;

    private final JWTProvider jwtProvider;

    // 인증 객체(Authentication)을 만들기 시도
    // attemptAuthentication 추상메소드의 구현은 상속한 UsernamePasswordAuthenticationFilter에 구현 되어 있습니다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getNotionId(),
                    user.getPassword()
            );

            /*
            - Filter를 통해 AuthenticationToken을 AuthenticationManager에 위임한다.
               UsernamePasswordAuthenticationToken 오브젝트가 생성된 후, AuthenticationManager의 인증 메소드를 호출한다.
            - PrincipalDetailsService의 loadUserByUsername() 함수가 실행된다.
            => 정상이면 authentication이 반환된다.
            * */
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            /*
            - authentication 객체가 session 영역에 정보를 저장한다. -> 로그인 처리
            - authenticatino 객체가 세션에 저장한 '방식'을 반환한다.
            - 참고 : security가 권한을 관리해주기 때문에 굳이 JWT에서는 세션을 만들필요는 없지만, 권한 처리를 위해 session을 사용한다.
             */

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("Authentication 확인: "+principalDetails.getUser().getNotionId());

            return  authentication;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // attemptAuthentication 메소드가 호출 된 후 동작
    // response에 JWT 토큰을 담아서 보내준다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        Long id = principalDetails.getUser().getId();
        String bojHandle = principalDetails.getUser().getBojHandle();
        String notionId = principalDetails.getUser().getNotionId();

        String jwtToken = jwtProvider.generateJwtToken(id, bojHandle, notionId);

        response.addHeader("Authorization", "Bearer " + jwtToken);

    }
}