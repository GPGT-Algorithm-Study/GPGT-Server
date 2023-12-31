package com.randps.randomdefence.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.mock.FakeUserRepository;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PrincipalDetailServiceTest {

    private PrincipalDetailsService principalDetailsService;

    @Test
    public void loadUserByUsername을_이용하여_bojHandle로_유저를_조회할_수_있다() {
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
    public void loadUserByUsername을_이용하여_bojHandle로_존재하지_않는_유저를_조회하면_에러를_던진다() {
        // given
        UserRepository userRepository = new FakeUserRepository();
        this.principalDetailsService = new PrincipalDetailsService(userRepository);

        // when & then
        assertThatThrownBy(() -> principalDetailsService.loadUserByUsername("fin")).isInstanceOf(UsernameNotFoundException.class);
    }

}
