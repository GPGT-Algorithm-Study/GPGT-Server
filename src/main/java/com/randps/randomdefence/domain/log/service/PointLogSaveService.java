package com.randps.randomdefence.domain.log.service;

import com.randps.randomdefence.domain.log.domain.PointLog;
import com.randps.randomdefence.domain.log.service.port.PointLogRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class PointLogSaveService {

    private final PointLogRepository pointLogRepository;

    private final UserRepository userRepository;

    /*
     * 특정 유저의 포인트 변경을 로그로 기록한다.
     */
    @Transactional
    public PointLog savePointLog(String bojHandle, Integer changedValue, String description, Boolean state) {
        PointLog pointLog = PointLog.builder().bojHandle(bojHandle).changedValue(changedValue).description(description)
                .state(state).build();
        pointLogRepository.save(pointLog);

        return pointLog;
    }

    /*
     * 특정 유저의 포인트 변경을 로그로 기록하고 반영한다. (Admin)
     */
    @Transactional
    public PointLog saveAndApplyPointLog(String bojHandle, Integer changedValue, String description, Boolean state) {
        User user = userRepository.findByBojHandle(bojHandle)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Boolean result;

        if (changedValue > 0) {
            result = user.increasePoint(changedValue);
        } else {
            result = user.decreasePoint(Math.abs(changedValue));
        }
        if (!result) {
            throw new IllegalArgumentException("포인트 (부여/차감)에 실패했습니다.");
        }
        userRepository.save(user);

        PointLog pointLog = PointLog.builder().bojHandle(bojHandle).changedValue(changedValue).description(description)
                .state(state).build();
        pointLogRepository.save(pointLog);

        return pointLog;
    }

    /*
     * 특정 포인트 로그를 되돌린다.
     */
    @Transactional
    public PointLog rollbackPointLog(Long logId) {
        PointLog pointLog = pointLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로그입니다."));
        User user = userRepository.findByBojHandle(pointLog.getBojHandle())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (pointLog.getState()) {
            // 유저의 포인트 변화를 되돌린다.
            user.decreasePoint(pointLog.getChangedValue());
            userRepository.save(user);

            // 포인트 로그의 상태를 취소로 바꾼다.
            pointLog.setStateNo();
        } else {
            // 유저의 포인트 변화를 되돌린다.
            user.increasePoint(pointLog.getChangedValue());
            userRepository.save(user);

            // 포인트 로그의 상태를 취소로 바꾼다.
            pointLog.setStateOk();
        }
        pointLogRepository.save(pointLog);
        return pointLog;
    }

    /*
     * 특정 유저의 모든 포인트 로그를 삭제한다.
     */
    @Transactional
    public void deleteAllPointLog(String bojHandle) {
        pointLogRepository.deleteAllByBojHandle(bojHandle);
    }

}
