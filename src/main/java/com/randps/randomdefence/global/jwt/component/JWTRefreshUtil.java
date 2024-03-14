package com.randps.randomdefence.global.jwt.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.randps.randomdefence.domain.user.service.PrincipalDetailsService;
import com.randps.randomdefence.global.jwt.component.port.RefreshTokenRepository;
import com.randps.randomdefence.global.jwt.domain.RefreshToken;
import com.randps.randomdefence.global.jwt.dto.TokenDto;
import java.security.cert.CertificateExpiredException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTRefreshUtil {

    private final PrincipalDetailsService principalDetailsService;

    private final RefreshTokenRepository refreshTokenRepository;

    private static final long ACCESS_TIME = 60L * 1000L;  // 만료 시간 1분
    private static final long REFRESH_TIME = 24L * 60L * 60L * 1000L; // 만료 시간 24시간
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";

    @Value("${jwt.secret}")
    private final String secretKey;

    private Algorithm getSign() {
        return Algorithm.HMAC512(Base64.getEncoder().encodeToString(this.secretKey.getBytes()));
    }

    // header 토큰을 가져오는 기능
    public String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
    }

    // 토큰 생성
    public TokenDto createAllToken(String nickname) {
        return new TokenDto(createToken(nickname, "Access"), createToken(nickname, "Refresh"));
    }


    public String createToken(String nickname, String type) {

        Date date = new Date();

        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;

        return JWT.create().withSubject(nickname).withExpiresAt(new Date(System.currentTimeMillis() + time))
                .withIssuedAt(date).withClaim("bojHandle", nickname).sign(this.getSign());
    }

    // 토큰 검증
    public Boolean tokenValidation(String token) {
        try {
            JWT.require(this.getSign()).build().verify(token).getClaim("bojHandle");
//            JWT.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    /*
     * 토큰에서 백준 핸들을 뽑아낸다.
     */
    public String getBojHandle(String token) throws CertificateExpiredException {
        try {
            return JWT.require(this.getSign()).build().verify(token).getClaim("bojHandle").asString();
        } catch (Exception ex) {
            throw new CertificateExpiredException("토큰 인증이 실패했습니다.");
        }
    }

    // refreshToken 토큰 검증
    // db에 저장되어 있는 token과 비교
    // db에 저장한다는 것이 jwt token을 사용한다는 강점을 상쇄시킨다.
    // db 보다는 redis를 사용하는 것이 더욱 좋다. (in-memory db기 때문에 조회속도가 빠르고 주기적으로 삭제하는 기능이 기본적으로 존재합니다.)
    public Boolean refreshTokenValidation(String token) {

        // 1차 토큰 검증
        if (!tokenValidation(token)) {
            return false;
        }

        // DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByBojHandle(getBojHandleFromToken(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String bojHandle) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(bojHandle);
        // spring security 내에서 가지고 있는 객체입니다. (UsernamePasswordAuthenticationToken)
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 email 가져오는 기능
    public String getBojHandleFromToken(String token) {
        return JWT.require(this.getSign()).build().verify(token).getClaim("bojHandle").asString();
    }

    // 어세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Access_Token", accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("Refresh_Token", refreshToken);
    }

    // 리프레쉬 토큰 삭제
    public void deleteRefreshToken(String bojHandle) {
        refreshTokenRepository.deleteByBojHandle(bojHandle);
    }
}
