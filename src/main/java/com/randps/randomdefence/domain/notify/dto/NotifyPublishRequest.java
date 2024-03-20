package com.randps.randomdefence.domain.notify.dto;

import com.randps.randomdefence.domain.notify.enums.NotifyType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotifyPublishRequest {

  private String receiver;

  private String message;

  private NotifyType type;

}
