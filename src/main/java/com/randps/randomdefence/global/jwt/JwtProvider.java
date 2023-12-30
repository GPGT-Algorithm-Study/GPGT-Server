package com.randps.randomdefence.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private final UserRepository userRepository;
    static Long EXPIRE_TIME = 60L * 60L * 1000L; // 만료 시간 1시간

    @Value("${jwt.secret}")
    private String secretKey;

    private Algorithm getSign(){
        return Algorithm.HMAC512(secretKey);
    }
    //객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }


    // Jwt 토큰 생성
    public String generateJwtToken(Long id, String bojHandle, String notionId){

        Date tokenExpiration = new Date(System.currentTimeMillis() + (EXPIRE_TIME));

        return JWT.create()
                .withSubject(bojHandle) //토큰 이름
                .withExpiresAt(tokenExpiration)
                .withClaim("id", id)
                .withClaim("bojHandle", bojHandle)
                .withClaim("notionId", notionId)
                .sign(this.getSign());
    }

    /**
     * 토큰 검증
     *  - 토큰에서 가져온 email 정보와 DB의 유저 정보 일치하는지 확인
     *  - 토큰 만료 시간이 지났는지 확인
     * @param token
     * @return 유저 객체 반환
     */
    public User validToken(String token){
        try {

            String bojHandle = JWT.require(this.getSign())
                    .build().verify(token).getClaim("bojHandle").asString();

            // 비어있는 값이다.
            if (bojHandle == null){
                return null;
            }

            // EXPIRE_TIME이 지나지 않았는지 확인
            Date expiresAt = JWT.require(this.getSign()).acceptExpiresAt(EXPIRE_TIME).build().verify(token).getExpiresAt();
            if (!this.validExpiredTime(expiresAt)) {
                // 만료시간이 지났다.
                return null;
            }

            return userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저의 토큰입니다."));

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    // 만료 시간 검증
    private boolean validExpiredTime(Date expiresAt){
        // LocalDateTime으로 만료시간 변경
        LocalDateTime localTimeExpired = expiresAt.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        // 현재 시간이 만료시간의 이전이다
        return LocalDateTime.now().isBefore(localTimeExpired);

    }

}
