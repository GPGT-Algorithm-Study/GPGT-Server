package com.randps.randomdefence.domain.notify.service.port;

import com.randps.randomdefence.domain.notify.domain.Notify;
import java.util.List;
import java.util.Optional;

public interface NotifyRepository {

  Notify save(Notify notify);

  void delete(Notify notify);

  List<Notify> saveAll(List<Notify> notifyList);

  List<Notify> findAll();

  List<Notify> findByReceiver(String receiver);

  List<Notify> findAllByIsReadIsFalseAndReceiver(String receiver);

  List<Notify> findAllByIsReadIsFalse();

  Optional<Notify> findById(Long id);

  void deleteById(Long id);

  void deleteAllByReceiver(String receiver);

}
