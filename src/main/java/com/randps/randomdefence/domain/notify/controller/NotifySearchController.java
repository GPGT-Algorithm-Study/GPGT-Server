package com.randps.randomdefence.domain.notify.controller;

import com.randps.randomdefence.domain.notify.dto.NotifyResponse;
import com.randps.randomdefence.domain.notify.service.NotifySearchService;
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
@RequestMapping("/api/v1/notify/search")
public class NotifySearchController {

  private final NotifySearchService notifySearchService;

  private final JWTRefreshUtil jwtRefreshUtil;

  /*
   * 사용자가 자신에게 온 알림을 조회한다.
   */
  @GetMapping("/receiver")
  public List<NotifyResponse> searchByReceiver(@RequestHeader("Refresh_Token") String refresh, @Param("receiver") String receiver)
      throws CertificateExpiredException, AccessDeniedException {
    return notifySearchService.findAllByReceiver(receiver, jwtRefreshUtil.getBojHandle(refresh));
  }

}
