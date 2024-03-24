package com.randps.randomdefence.global.component.mock;

import com.randps.randomdefence.global.component.util.port.Clock;
import java.time.LocalDateTime;

public class FakeClock implements Clock {

  private LocalDateTime now = null;

  @Override
  public LocalDateTime now() {
    if (now != null) {
      return now;
    }
    return LocalDateTime.now();
  }

  public void setNow(LocalDateTime now) {
    this.now = now;
  }

}
