package com.randps.randomdefence.domain.user.controller;

import static com.randps.randomdefence.global.component.util.ResponseUtil.toResponse;

import com.randps.randomdefence.domain.user.service.UserSettingToggleService;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/setting/toggle")
public class UserSettingToggleController {

  private final UserSettingToggleService userSettingToggleService;

  @PutMapping("/warning")
  public ResponseEntity<Map<String, String>> toggleWarningSetting(@NotBlank String bojHandle) {
    userSettingToggleService.toggleWarningSetting(bojHandle);
    return toResponse(HttpStatus.OK, "200", "유저의 경고 설정을 성공적으로 변경했습니다.");
  }

  @PutMapping("/scraping")
  public ResponseEntity<Map<String, String>> toggleScrapingSetting(@NotBlank String bojHandle) {
    userSettingToggleService.toggleScrapingSetting(bojHandle);
    return toResponse(HttpStatus.OK, "200", "유저의 크롤링 설정을 성공적으로 변경했습니다.");
  }

}
