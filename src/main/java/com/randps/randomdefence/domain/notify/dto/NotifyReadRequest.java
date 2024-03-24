package com.randps.randomdefence.domain.notify.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotifyReadRequest {

  private Long id;

  @Builder
  public NotifyReadRequest(Long id) {
    this.id = id;
  }

}
