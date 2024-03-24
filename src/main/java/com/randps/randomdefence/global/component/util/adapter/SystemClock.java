package com.randps.randomdefence.global.component.util.adapter;

import com.randps.randomdefence.global.component.util.port.Clock;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class SystemClock implements Clock {

  @Override
  public LocalDateTime now() {
    return LocalDateTime.now();
  }

}
