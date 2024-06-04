package com.randps.randomdefence.global.config;

import com.randps.randomdefence.domain.user.service.port.UserRepository;
import com.randps.randomdefence.global.config.filter.JwtRefreshAuthFilter;
import com.randps.randomdefence.global.jwt.component.JWTProvider;
import com.randps.randomdefence.global.jwt.component.JWTRefreshUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;

  private final UserRepository userRepository;

  private final JWTRefreshUtil jwtUtil;

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public JWTProvider jwtTokenProvider() {
    return new JWTProvider(userRepository);
  }

  // 회원의 패스워드 암호화
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {

    return new BCryptPasswordEncoder();
  }

  // 시큐리티 필터 설정
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS, "/api/v1/**").permitAll()
        .antMatchers("/api/v1/user/auth/login", "/api/v1/user/auth/logout", "/api/v1/user/add/all",
            "/api/v1/user/admin/init", "/api/v1/recommend").permitAll()
        .antMatchers("/api/v1/user/sejong/register/**").permitAll()
        .antMatchers("/api/v1/user/add", "/api/v1/user/del", "/api/v1/scraping/*",
            "api/v1/admin/*", "/api/v1/complaint/processor/*", "/api/v1/user/setting/*")
        .hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .cors().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin().disable()
        .httpBasic().disable() // http의 기본 인증. ID, PW 인증방식
        .addFilterBefore(new JwtRefreshAuthFilter(jwtUtil),
            UsernamePasswordAuthenticationFilter.class);

    return http.build();

  }
}
