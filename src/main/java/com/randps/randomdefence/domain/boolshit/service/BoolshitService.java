package com.randps.randomdefence.domain.boolshit.service;

import com.randps.randomdefence.domain.boolshit.domain.Boolshit;
import com.randps.randomdefence.domain.boolshit.dto.BoolshitResponse;
import com.randps.randomdefence.domain.boolshit.service.port.BoolshitRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Builder
@Service
public class BoolshitService {

    private final UserRepository userRepository;

    private final BoolshitRepository boolshitRepository;

    /*
     * 가장 최근 나의 한마디를 조회한다.
     */
    public BoolshitResponse findLast() {
        List<Boolshit> boolshitList = boolshitRepository.findAll();

        // 아직 나의 한마디가 존재하지 않는다면 기본 값을 반환한다.
        if (boolshitList.isEmpty()) {
            return BoolshitResponse.builder().id(0L).message("아직 나의 한마디가 존재하지 않습니다.").user(null).build();
        }

        // 마지막 나의 한마디를 가져온다.
        Boolshit boolshit = boolshitList.get(boolshitList.size() - 1);

        // 헛소리를 한 유저를 가져온다.
        User user = userRepository.findByBojHandle(boolshit.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // dto로 만들어 반환한다.
        return BoolshitResponse.builder()
                .id(boolshit.getId())
                .message(boolshit.getMessage())
                .user(user.toUserInfoResponse())
                .writtenDate(boolshit.getCreatedDate())
                .build();
    }

    /*
     * 모든 나의 한마디를 조회한다.
     */
    public List<BoolshitResponse> findAll() {
        List<Boolshit> boolshitList = boolshitRepository.findAll();
        List<BoolshitResponse> boolshitResponses = new ArrayList<>();

        for (Boolshit boolshit : boolshitList) {
            // 헛소리를 한 유저를 가져온다.
            User user = userRepository.findByBojHandle(boolshit.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

            boolshitResponses.add(BoolshitResponse.builder()
                    .id(boolshit.getId())
                    .message(boolshit.getMessage())
                    .user(user.toUserInfoResponse())
                    .writtenDate(boolshit.getCreatedDate())
                    .build());
        }

        return boolshitResponses;
    }

    /*
     * 나의 한마디를 추가한다.
     */
    @Transactional
    public void add(String massage, String bojHandle) {
        Boolshit boolshit = Boolshit.builder()
                .message(massage)
                .bojHandle(bojHandle)
                .build();
        boolshitRepository.save(boolshit);
    }

    /*
     * 나의 한마디를 삭제한다.
     */
    @Transactional
    public void deleteAllByBojHandle(String bojHandle) {
        boolshitRepository.deleteAllByBojHandle(bojHandle);
    }
}
