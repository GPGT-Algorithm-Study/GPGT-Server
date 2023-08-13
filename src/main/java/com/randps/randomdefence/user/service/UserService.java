package com.randps.randomdefence.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.component.parser.BojParserImpl;
import com.randps.randomdefence.component.parser.SolvedacParserImpl;
import com.randps.randomdefence.component.query.Query;
import com.randps.randomdefence.component.query.SolvedacQueryImpl;
import com.randps.randomdefence.user.domain.User;
import com.randps.randomdefence.user.domain.UserRepository;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    /*
     * 유저를 DB에 저장한다.
     */
    @Transactional
    public User save(String bojHandle, String notionId, Long manager, String emoji) {
        Optional<User> isExistUser = userRepository.findByBojHandle(bojHandle);
        if (isExistUser.isPresent()) {
            return isExistUser.get();
        }
        if (!(manager == 0 || manager == 1)) {
            throw new IllegalArgumentException("잘못된 파라미터가 전달되었습니다.");
        }

        User user = User.builder()
                .bojHandle(bojHandle)
                .notionId(notionId)
                .manager(manager==1?true:false)
                .warning(0)
                .profileImg("")
                .emoji(emoji)
                .tier(0)
                .totalSolved(0)
                .currentStreak(0)
                .currentRandomStreak(0)
                .team(0)
                .point(0)
                .isTodaySolved(false)
                .build();

        userRepository.save(user);

        return user;
    }

    /*
     * 유저를 DB에서 삭제한다.
     */
    @Transactional
    public void delete(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        userRepository.delete(user);
    }
}
