package com.randps.randomdefence.domain.complaint.infrastructure;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import com.randps.randomdefence.domain.complaint.service.port.ComplaintRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ComplaintJPARepositoryAdapter implements ComplaintRepository {

  private final ComplaintJPARepository complaintJPARepository;

  @Override
  public Complaint save(Complaint complaint) {
    return complaintJPARepository.save(complaint);
  }

  @Override
  public void delete(Complaint complaint) {
    complaintJPARepository.delete(complaint);
  }

  @Override
  public List<Complaint> findAll() {
    return complaintJPARepository.findAll();
  }

  @Override
  public List<Complaint> findAllByOrderByIdDesc() {
    return complaintJPARepository.findAllByOrderByIdDesc();
  }

  @Override
  public List<Complaint> findAllByRequester(String requester) {
    return complaintJPARepository.findAllByRequester(requester);
  }

  @Override
  public List<Complaint> findAllByProcessor(String processor) {
    return complaintJPARepository.findAllByProcessor(processor);
  }

  @Override
  public List<Complaint> findAllByComplaintType(ComplaintType complaintType) {
    return complaintJPARepository.findAllByComplaintType(complaintType);
  }

  @Override
  public List<Complaint> findAllByProcessType(ProcessType processType) {
    return complaintJPARepository.findAllByProcessType(processType);
  }

  @Override
  public Optional<Complaint> findById(Long id) {
    return complaintJPARepository.findById(id);
  }
}
