package com.randps.randomdefence.domain.notify.dto;

import com.randps.randomdefence.domain.notify.enums.NotifyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyUpdateRequest {

  private Long id;

  private String message;

  private NotifyType type;

}
