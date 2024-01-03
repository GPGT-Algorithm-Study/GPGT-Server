package com.randps.randomdefence.domain.boolshit.service;

import com.randps.randomdefence.domain.boolshit.dto.BoolshitLastResponse;
import com.randps.randomdefence.domain.boolshit.service.port.BoolshitRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoolshitServiceV2 {

    private final BoolshitRepository boolshitRepository;

    /**
     * 가장 최근 나의 한마디를 조회한다. (Qeurydsl)
     */
    @Transactional
    public BoolshitLastResponse findLast() {
        Optional<BoolshitLastResponse> boolshitLastResponse = boolshitRepository.findLastBoolshit();

        // 아직 나의 한마디가 존재하지 않는다면 기본 값을 반환한다.
        return boolshitLastResponse.orElseGet(
                () -> BoolshitLastResponse.builder().id(0L).message("아직 나의 한마디가 존재하지 않습니다.").notionId(null).emoji(null)
                        .build());

    }
}
