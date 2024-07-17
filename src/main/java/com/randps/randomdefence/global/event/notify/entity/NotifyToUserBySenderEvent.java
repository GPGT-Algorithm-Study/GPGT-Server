package com.randps.randomdefence.global.event.notify.entity;

import com.randps.randomdefence.domain.notify.dto.NotifyPublishRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class NotifyToUserBySenderEvent extends ApplicationEvent {

  private NotifyPublishRequest request;

  private String sender;

  public NotifyToUserBySenderEvent(Object source, NotifyPublishRequest request, String sender) {
    super(source);
    this.request = request;
    this.sender = sender;
  }

}
