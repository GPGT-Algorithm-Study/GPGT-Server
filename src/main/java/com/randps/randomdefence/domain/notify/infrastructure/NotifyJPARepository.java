package com.randps.randomdefence.domain.notify.infrastructure;

import com.randps.randomdefence.domain.notify.domain.Notify;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyJPARepository extends JpaRepository<Notify, Long> {

  List<Notify> findByReceiver(String receiver);

  List<Notify> findAllByIsReadIsFalseAndReceiver(String receiver);

  List<Notify> findAllByIsReadIsFalse();

  void deleteAllByReceiver(String receiver);

}
