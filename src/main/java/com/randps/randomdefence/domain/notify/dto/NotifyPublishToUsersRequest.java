package com.randps.randomdefence.domain.notify.dto;

import com.randps.randomdefence.domain.notify.enums.NotifyType;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyPublishToUsersRequest {

  public List<String> receivers;

  public String message;

  public NotifyType type;

}
