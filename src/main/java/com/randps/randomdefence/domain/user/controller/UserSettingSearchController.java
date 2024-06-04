package com.randps.randomdefence.domain.user.controller;

import com.randps.randomdefence.domain.user.dto.UserSettingResponse;
import com.randps.randomdefence.domain.user.service.UserSettingSearchService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/setting")
public class UserSettingSearchController {

  private final UserSettingSearchService userSettingSearchService;

  @GetMapping("/all")
  public List<UserSettingResponse> findAllSafe() {
    return userSettingSearchService.findAllSafe().stream().map(UserSettingResponse::from).collect(
        Collectors.toList());
  }

  @GetMapping
  public UserSettingResponse findByBojHandleSafe(@NotBlank String bojHandle) {
    return UserSettingResponse.from(userSettingSearchService.findByBojHandleSafe(bojHandle));
  }

}
