package com.randps.randomdefence.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.mock.FakeUserRepository;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PrincipalDetailServiceTest {

    private PrincipalDetailsService principalDetailsService;

    @AfterEach
    void tearDown() {
        principalDetailsService = null;
    }

    @Test
    @DisplayName("loadUserByUsername을 이용하여 bojHandle로 유저를 조회할 수 있다")
    public void loadUserByUsernameTest() {
        // given
        UserRepository userRepository = new FakeUserRepository();
        this.principalDetailsService = new PrincipalDetailsService(userRepository);
        User user = User.builder()
                .id(1L)
                .bojHandle("fin")
                .password("q1w2e3r4!")
                .roles("Admin")
                .notionId("성민")
                .manager(true)
                .warning(1)
                .profileImg("https://static.solved.ac/uploads/profile/64x64/fin-picture-1665752455693.png")
                .emoji("🔥")
                .tier(15)
                .totalSolved(1067)
                .currentStreak(252)
                .currentRandomStreak(92)
                .team(1)
                .point(1234)
                .isTodaySolved(true)
                .isYesterdaySolved(true)
                .isTodayRandomSolved(true)
                .todaySolvedProblemCount(1)
                .build();
        userRepository.save(user);

        // when
        UserDetails result = principalDetailsService.loadUserByUsername("fin");

        // then
        assertThat(result.getUsername()).isEqualTo("성민");
        assertThat(result.getPassword()).isEqualTo("q1w2e3r4!");
        assertThat(result.getAuthorities()).isNotNull();
    }

    @Test
    @DisplayName("loadUserByUsername을 이용하여 bojHandle로 존재하지 않는 유저를 조회하면 에러를 던진다")
    public void loadUserByUsernameExceptionTest() {
        // given
        UserRepository userRepository = new FakeUserRepository();
        this.principalDetailsService = new PrincipalDetailsService(userRepository);

        // when & then
        assertThatThrownBy(() -> principalDetailsService.loadUserByUsername("fin")).isInstanceOf(UsernameNotFoundException.class);
    }

}
