package com.randps.randomdefence.global.event.notify.entity;

import com.randps.randomdefence.domain.notify.enums.NotifyType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class NotifyToAllEvent extends ApplicationEvent {

  private String message;

  private NotifyType type;

  private Long relatedBoardId;

  public NotifyToAllEvent(Object source, String message, NotifyType type, Long relatedBoardId) {
    super(source);
    this.message = message;
    this.type = type;
    this.relatedBoardId = relatedBoardId;
  }
}
