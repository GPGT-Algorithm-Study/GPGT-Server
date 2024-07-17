package com.randps.randomdefence.global.event.notify.entity;

import com.randps.randomdefence.domain.notify.enums.NotifyType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class NotifyToUserBySystemEvent extends ApplicationEvent {

  private String receiver;

  private String message;

  private NotifyType type;

  private Long relatedBoardId;

  public NotifyToUserBySystemEvent(Object source, String receiver, String message, NotifyType type,
      Long relatedBoardId) {
    super(source);
    this.receiver = receiver;
    this.message = message;
    this.type = type;
    this.relatedBoardId = relatedBoardId;
  }

}
