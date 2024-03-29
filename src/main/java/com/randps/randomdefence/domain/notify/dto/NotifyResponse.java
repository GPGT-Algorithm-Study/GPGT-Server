package com.randps.randomdefence.domain.notify.dto;

import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.enums.NotifyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyResponse {

  private Long id;

  private String receiver;

  private String message;

  private NotifyType type;

  private Boolean isRead;

  public static NotifyResponse of(Long id, String receiver, String message, NotifyType type, Boolean isRead) {
    return NotifyResponse.builder()
        .id(id)
        .receiver(receiver)
        .message(message)
        .type(type)
        .isRead(isRead)
        .build();
  }

  public static NotifyResponse of(Notify notify) {
    return NotifyResponse.builder()
        .id(notify.getId())
        .receiver(notify.getReceiver())
        .message(notify.getMessage())
        .type(notify.getType())
        .isRead(notify.getIsRead())
        .build();
  }

}
