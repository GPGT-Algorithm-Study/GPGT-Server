package com.randps.randomdefence.domain.notify.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.randps.randomdefence.domain.notify.dto.NotifyResponse;
import com.randps.randomdefence.domain.notify.service.port.NotifyRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Service
@RequiredArgsConstructor
public class NotifyAdminSearchService {

  private final NotifyRepository notifyRepository;

  private final UserRepository userRepository;

  /*
   * 관리자가 모든 알림을 조회한다.
   */
  public List<NotifyResponse> findAll(String bojHandle) {
    User adminUser = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    if (!adminUser.getManager()) {
      throw new IllegalArgumentException("권한이 없습니다.");
    }

    return notifyRepository.findAll().stream().map(NotifyResponse::of).collect(
        Collectors.toList());
  }

  /*
   * 관리자가 특정 사용자에게 온 모든 알림을 조회한다.
   */
  public List<NotifyResponse> findAllByReceiver(String receiver, String bojHandle) {
    User adminUser = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    if (!adminUser.getManager()) {
      throw new IllegalArgumentException("권한이 없습니다.");
    }

    return notifyRepository.findByReceiver(receiver).stream().map(NotifyResponse::of).collect(
        Collectors.toList());
  }

  /*
   * 관리자가 아직 사람들이 읽지 않은 모든 알림을 조회한다.
   */
  public List<NotifyResponse> findNotReadNotifiesAll(String bojHandle) {
    User adminUser = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    if (!adminUser.getManager()) {
      throw new IllegalArgumentException("권한이 없습니다.");
    }

    return notifyRepository.findAllByIsReadIsFalse().stream().map(NotifyResponse::of).collect(
        Collectors.toList());
  }

}
