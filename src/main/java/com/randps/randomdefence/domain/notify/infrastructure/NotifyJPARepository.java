package com.randps.randomdefence.domain.notify.infrastructure;

import com.randps.randomdefence.domain.notify.domain.Notify;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyJPARepository extends JpaRepository<Notify, Long> {

  List<Notify> findByReceiverOrderByIdDesc(String receiver);

  List<Notify> findAllByIsReadIsFalseAndReceiverOrderByIdDesc(String receiver);

  List<Notify> findAllByIsReadIsFalseOrderByIdDesc();

  void deleteAllByReceiver(String receiver);

}
