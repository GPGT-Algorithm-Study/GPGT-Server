package com.randps.randomdefence.domain.notify.infrastructure;

import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.service.port.NotifyRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotifyJPARepositoryAdapter implements NotifyRepository {

  private final NotifyJPARepository notifyJPARepository;


  @Override
  public Notify save(Notify notify) {
    return notifyJPARepository.save(notify);
  }

  @Override
  public void delete(Notify notify) {
    notifyJPARepository.delete(notify);
  }

  @Override
  public List<Notify> saveAll(List<Notify> notifyList) {
    return notifyJPARepository.saveAll(notifyList);
  }

  @Override
  public List<Notify> findAll() {
    return notifyJPARepository.findAll();
  }

  @Override
  public List<Notify> findByReceiver(String receiver) {
    return notifyJPARepository.findByReceiverOrderByIdDesc(receiver);
  }

  @Override
  public List<Notify> findAllByIsReadIsFalseAndReceiver(String receiver) {
    return notifyJPARepository.findAllByIsReadIsFalseAndReceiverOrderByIdDesc(receiver);
  }

  @Override
  public List<Notify> findAllByIsReadIsFalse() {
    return notifyJPARepository.findAllByIsReadIsFalseOrderByIdDesc();
  }

  @Override
  public Optional<Notify> findById(Long id) {
    return notifyJPARepository.findById(id);
  }

  @Override
  public void deleteById(Long id) {
    notifyJPARepository.deleteById(id);
  }

  @Override
  public void deleteAllByReceiver(String receiver) {
    notifyJPARepository.deleteAllByReceiver(receiver);
  }

}
