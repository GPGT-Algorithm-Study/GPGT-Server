package com.randps.randomdefence.user.service;

import com.randps.randomdefence.component.query.Query;
import com.randps.randomdefence.component.query.SolvedacQueryImpl;
import com.randps.randomdefence.user.domain.User;
import com.randps.randomdefence.user.domain.UserRepository;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(String bojHandle, String notionId, Long manager) {
        if (!(manager == 0 || manager == 1)) {
            throw new IllegalArgumentException("잘못된 파라미터가 전달되었습니다.");
        }
        User user = User.builder()
                .bojHandle(bojHandle)
                .notionId(notionId)
                .manager(manager==1?true:false)
                .build();

        userRepository.save(user);

        return user;
    }

    @Transactional
    public void delete(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        userRepository.delete(user);
    }

    @Transactional
    public String makeQuery(String bojHandle) {
        Query query = new SolvedacQueryImpl();

        query.setDomain("https://solved.ac/api/v3/user/show");
        query.setParam("handle", bojHandle);

        return query.getQuery();
    }

    //TODO: jsoup으로 백준 or solved 프로필 파싱
    @Transactional
    public UserInfoResponse getInfo(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Query query = new SolvedacQueryImpl();

        query.setDomain("https://solved.ac/api/v3/search/problem");
        query.setParam("handle", bojHandle);

        return new UserInfoResponse();
    }
}
