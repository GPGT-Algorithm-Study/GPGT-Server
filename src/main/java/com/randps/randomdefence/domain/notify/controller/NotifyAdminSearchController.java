package com.randps.randomdefence.domain.notify.controller;

import com.randps.randomdefence.domain.notify.dto.NotifyResponse;
import com.randps.randomdefence.domain.notify.service.NotifyAdminSearchService;
import com.randps.randomdefence.global.jwt.component.JWTRefreshUtil;
import java.nio.file.AccessDeniedException;
import java.security.cert.CertificateExpiredException;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Builder
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify/admin/search")
public class NotifyAdminSearchController {

  private final NotifyAdminSearchService notifyAdminSearchService;

  private final JWTRefreshUtil jwtRefreshUtil;

  /*
   * 관리자가 모든 알림을 조회한다.
   */
  @GetMapping("/admin/all")
  public List<NotifyResponse> searchAll(@RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {
    return notifyAdminSearchService.findAll(jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자가 특정 사용자에게 온 모든 알림을 조회한다.
   */
  @GetMapping("/admin/receiver/all")
  public List<NotifyResponse> searchByReceiver(@RequestHeader("Refresh_Token") String refresh, @Param("receiver") String receiver)
      throws CertificateExpiredException, AccessDeniedException {
    return notifyAdminSearchService.findAllByReceiver(receiver, jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자가 아직 사람들이 읽지 않은 모든 알림을 조회한다.
   */
  @GetMapping("/admin/notRead/all")
  public List<NotifyResponse> searchNotReadNotifiesAll(@RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {
    return notifyAdminSearchService.findNotReadNotifiesAll(jwtRefreshUtil.getBojHandle(refresh));
  }

}
