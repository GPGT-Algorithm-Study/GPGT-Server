package com.randps.randomdefence.global.event.notify.handler;

import com.randps.randomdefence.domain.notify.service.NotifyService;
import com.randps.randomdefence.global.event.notify.entity.NotifyToAdminEvent;
import com.randps.randomdefence.global.event.notify.entity.NotifyToAllEvent;
import com.randps.randomdefence.global.event.notify.entity.NotifyToUserBySenderEvent;
import com.randps.randomdefence.global.event.notify.entity.NotifyToUserBySystemEvent;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotifyEventHandler {

  private final NotifyService notifyService;

  @EventListener
  public void handlePublishToAllEvent(NotifyToAllEvent event) {
    notifyService.systemPublishToAll(event.getMessage(), event.getType(),
        event.getRelatedBoardId());
  }

  @EventListener
  public void handlePublishToAdminEvent(NotifyToAdminEvent event) {
    notifyService.systemPublishToAdmins(event.getMessage(), event.getType(),
        event.getRelatedBoardId());
  }

  @EventListener
  public void handlePublishToUserBySenderEvent(NotifyToUserBySenderEvent event)
      throws AccessDeniedException {
    notifyService.publish(event.getRequest(), event.getSender());
  }

  @EventListener
  public void handlePublishToUserBySystemEvent(NotifyToUserBySystemEvent event) {
    notifyService.systemPublish(event.getReceiver(), event.getMessage(), event.getType(),
        event.getRelatedBoardId());
  }

}
