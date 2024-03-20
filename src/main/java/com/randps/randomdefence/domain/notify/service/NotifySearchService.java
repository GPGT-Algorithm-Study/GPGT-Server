package com.randps.randomdefence.domain.notify.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.dto.NotifyResponse;
import com.randps.randomdefence.domain.notify.service.port.NotifyRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Service
@RequiredArgsConstructor
public class NotifySearchService {

  private final NotifyRepository notifyRepository;

  private final UserRepository userRepository;

  /*
   * 사용자가 자신에게 온 모든 알림을 조회한다.
   */
  public List<NotifyResponse> findAllByReceiver(String receiver, String bojHandle)
      throws AccessDeniedException {
    User adminUser = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    if (!receiver.equals(bojHandle) && !adminUser.getManager()) {
      throw new AccessDeniedException("권한이 없습니다.");
    }

    return notifyRepository.findByReceiver(receiver).stream().map(NotifyResponse::of).collect(
        Collectors.toList());
  }

  /*
   * 사용자가 자신에게 온 아직 읽지 않은 모든 알림을 조회한다.
   */
  public List<NotifyResponse> findNotReadNotifiesAllByReceiver(String receiver, String bojHandle)
      throws AccessDeniedException {
    User adminUser = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    if (!receiver.equals(bojHandle) && !adminUser.getManager()) {
      throw new AccessDeniedException("권한이 없습니다.");
    }

    return notifyRepository.findAllByIsReadIsFalseAndReceiver(receiver).stream()
        .map(NotifyResponse::of).collect(
            Collectors.toList());
  }

  /*
   * 사용자가 자신에게 온 특정 알림을 읽음 처리한다.
   */
  @Transactional
  public void readByReceiver(String receiver, String bojHandle, Long notifyId)
      throws AccessDeniedException {
    User adminUser = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    if (!receiver.equals(bojHandle) && !adminUser.getManager()) {
      throw new AccessDeniedException("권한이 없습니다.");
    }

    Notify existNotify = notifyRepository.findById(notifyId).orElseThrow(() -> new NotFoundException("알림이 존재하지 않습니다."));
    existNotify.read();
    notifyRepository.save(existNotify);
  }


}
