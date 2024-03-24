package com.randps.randomdefence.domain.notify.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotifyDeleteRequest {

  private Long id;

  @Builder
  public NotifyDeleteRequest(Long id) {
    this.id = id;
  }

}
