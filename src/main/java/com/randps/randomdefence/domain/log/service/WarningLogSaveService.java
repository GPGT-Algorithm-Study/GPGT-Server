package com.randps.randomdefence.domain.log.service;

import com.randps.randomdefence.domain.log.domain.WarningLog;
import com.randps.randomdefence.domain.log.service.port.WarningLogRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class WarningLogSaveService {

    private final WarningLogRepository warningLogRepository;

    private final UserRepository userRepository;

    /*
     * 특정 유저의 경고 변경을 로그로 기록한다.
     */
    @Transactional
    public WarningLog saveWarningLog(String bojHandle, Integer changedValue, String description, Boolean state) {
        WarningLog warningLog = WarningLog.builder()
                .bojHandle(bojHandle)
                .changedValue(changedValue)
                .description(description)
                .state(state)
                .build();
        warningLogRepository.save(warningLog);

        return warningLog;
    }

    /*
     * 특정 유저의 경고 변경을 로그로 기록하고 반영한다. (Admin)
     */
    @Transactional
    public WarningLog saveAndApplyWarningLog(String bojHandle, Integer changedValue, String description, Boolean state) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        boolean result;

        if (changedValue > 0) {
            result = user.increaseWarning();
        } else {
            result = user.decreaseWarning();
        }
        if (!result) {
            throw new IllegalArgumentException("경고 (부여/차감)에 실패했습니다.");
        }
        userRepository.save(user);

        WarningLog warningLog = WarningLog.builder()
                .bojHandle(bojHandle)
                .changedValue(changedValue)
                .description(description)
                .state(state)
                .build();
        warningLogRepository.save(warningLog);

        return warningLog;
    }

    /*
     * 특정 경고 로그를 되돌린다.
     */
    @Transactional
    public WarningLog rollbackPointLog(Long logId) {
        WarningLog warningLog = warningLogRepository.findById(logId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로그입니다."));
        User user = userRepository.findByBojHandle(warningLog.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // 유저의 경고 변화를 되돌린다.
        user.decreaseWarning(warningLog.getChangedValue());
        userRepository.save(user);

        // 경고 로그의 상태를 취소로 바꾼다.
        warningLog.setStateNo();
        warningLogRepository.save(warningLog);

        return warningLog;
    }

    /*
     * 특정 유저의 모든 경고 로그를 삭제한다.
     */
    @Transactional
    public void deleteAllWaringLog(String bojHandle) {
        warningLogRepository.deleteAllByBojHandle(bojHandle);
    }
}