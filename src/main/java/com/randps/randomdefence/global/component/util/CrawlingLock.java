package com.randps.randomdefence.global.component.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Component;

@Component
public class CrawlingLock {

  private final Lock lock = new ReentrantLock();

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

  public Boolean isLocked() {
    Boolean ret = !lock.tryLock();
    if (!ret)
      lock.unlock();
    return ret;
  }

}
