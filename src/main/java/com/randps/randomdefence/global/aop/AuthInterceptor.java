package com.randps.randomdefence.global.aop;

import com.randps.randomdefence.global.jwt.component.JWTRefreshUtil;
import java.security.cert.CertificateExpiredException;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

  private final JWTRefreshUtil jwtRefreshUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws AuthenticationException, CertificateExpiredException {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod handlerMethod = (HandlerMethod) handler;

    // Auth 어노테이션이 없다면 통과한다.
    Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
    if (auth == null) {
      return true;
    }

    String refreshToken = request.getHeader("Refresh_Token");
    if (refreshToken == null) {
      throw new AuthenticationException("인증 정보가 바르지 않습니다.");
    }

    String tokenBojHandle = jwtRefreshUtil.getBojHandle(refreshToken);
    String roles = jwtRefreshUtil.getRoles(refreshToken);
    if (!roles.contains("ROLE_USER")) {
      throw new AuthenticationException("토큰이 유효하지 않습니다.");
    }

    // 권한 체크
    if (auth.role() == Auth.Role.USER) {
      if (!roles.contains("ROLE_USER") && !roles.contains("ROLE_ADMIN")) {
        throw new AccessDeniedException("권한이 없습니다.");
      }
    } else if (auth.role() == Auth.Role.ADMIN) {
      if (!roles.contains("ROLE_ADMIN")) {
        throw new AccessDeniedException("권한이 없습니다.");
      }
    }
    return true;
  }

}
