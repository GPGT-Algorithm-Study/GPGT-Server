package com.randps.randomdefence.domain.log.service;

import com.randps.randomdefence.domain.log.domain.PointLog;
import com.randps.randomdefence.domain.log.domain.PointLogRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PointLogSaveService {

    private final PointLogRepository pointLogRepository;

    private final UserRepository userRepository;

    /*
     * 특정 유저의 포인트 변경을 로그로 기록한다.
     */
    @Transactional
    public PointLog savePointLog(String bojHandle, Integer changedValue, String description, Boolean state) {
        PointLog pointLog = PointLog.builder()
                .bojHandle(bojHandle)
                .changedValue(changedValue)
                .description(description)
                .state(state)
                .build();
        pointLogRepository.save(pointLog);

        return pointLog;
    }

    /*
     * 특정 포인트 로그를 되돌린다.
     */
    @Transactional
    public PointLog rollbackPointLog(Long logId) {
        PointLog pointLog = pointLogRepository.findById(logId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로그입니다."));
        User user = userRepository.findByBojHandle(pointLog.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // 유저의 포인트 변화를 되돌린다.
        user.decreasePoint(pointLog.getChangedValue());
        userRepository.save(user);

        // 포인트 로그의 상태를 취소로 바꾼다.
        pointLog.setStateNo();
        pointLogRepository.save(pointLog);

        return pointLog;
    }
}
