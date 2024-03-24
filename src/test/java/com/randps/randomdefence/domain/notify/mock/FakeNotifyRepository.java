package com.randps.randomdefence.domain.notify.mock;

import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.service.port.NotifyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeNotifyRepository implements NotifyRepository {

  private final List<Notify> notifyList = new ArrayList<>();
  private final AtomicLong id = new AtomicLong(0);

  @Override
  public Notify save(Notify notify) {
    if (notify.getId() == null || notify.getId() == 0L) {
      Notify newNotify = Notify.builder()
          .id(id.incrementAndGet())
          .receiver(notify.getReceiver())
          .message(notify.getMessage())
          .type(notify.getType())
          .build();
      notifyList.add(newNotify);
      return newNotify;
    } else {
      notifyList.removeIf(item -> item.getId().equals(notify.getId()));
      Notify updatedNotify = Notify.builder()
          .id(notify.getId())
          .receiver(notify.getReceiver())
          .message(notify.getMessage())
          .type(notify.getType())
          .build();
      if (notify.getIsRead()) {
        updatedNotify.read();
      }
      notifyList.add(updatedNotify);
      return updatedNotify;
    }
  }

  @Override
  public void delete(Notify notify) {
    notifyList.removeIf(item -> item.getId().equals(notify.getId()));
  }

  @Override
  public List<Notify> saveAll(List<Notify> notifyList) {
    List<Notify> savedNotifyList = new ArrayList<>();
    for (Notify notify : notifyList) {
      savedNotifyList.add(save(notify));
    }
    return savedNotifyList;
  }

  @Override
  public List<Notify> findAll() {
    return notifyList;
  }

  @Override
  public List<Notify> findByReceiver(String receiver) {
    return notifyList.stream().filter(item -> item.getReceiver().equals(receiver)).collect(Collectors.toList());
  }

  @Override
  public List<Notify> findAllByIsReadIsFalseAndReceiver(String receiver) {
    return notifyList.stream().filter(item -> item.getReceiver().equals(receiver)).filter(item -> !item.getIsRead()).collect(Collectors.toList());
  }

  @Override
  public List<Notify> findAllByIsReadIsFalse() {
    return notifyList.stream().filter(item -> !item.getIsRead()).collect(Collectors.toList());
  }

  @Override
  public Optional<Notify> findById(Long id) {
    return notifyList.stream().filter(item -> item.getId().equals(id)).findFirst();
  }

  @Override
  public void deleteById(Long id) {
    notifyList.removeIf(item -> item.getId().equals(id));
  }

  @Override
  public void deleteAllByReceiver(String receiver) {
    notifyList.removeIf(item -> item.getReceiver().equals(receiver));
  }
}
