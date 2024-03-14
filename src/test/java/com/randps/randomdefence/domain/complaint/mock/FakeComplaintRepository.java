package com.randps.randomdefence.domain.complaint.mock;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import com.randps.randomdefence.domain.complaint.service.port.ComplaintRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeComplaintRepository implements ComplaintRepository {

  private final List<Complaint> data = new ArrayList<>();

  private Long autoIncreasingCount = 0L;

  @Override
  public List<Complaint> findAll() {
    return data;
  }

  @Override
  public List<Complaint> findAllByOrderByIdDesc() {
    return data.stream().sorted((o1, o2) -> (int) (o2.getId() - o1.getId())).collect(Collectors.toList());
  }

  @Override
  public List<Complaint> findAllByRequester(String requester) {
    return data.stream().filter(item -> item.getRequester().equals(requester)).collect(Collectors.toList());
  }

  @Override
  public List<Complaint> findAllByProcessor(String processor) {
    return data.stream().filter(item -> item.getProcessor().equals(processor)).collect(Collectors.toList());
  }

  @Override
  public List<Complaint> findAllByComplaintType(ComplaintType complaintType) {
    return data.stream().filter(item -> item.getComplaintType().equals(complaintType)).collect(Collectors.toList());
  }

  @Override
  public List<Complaint> findAllByProcessType(ProcessType processType) {
    return data.stream().filter(item -> item.getProcessType().equals(processType)).collect(Collectors.toList());
  }

  @Override
  public Optional<Complaint> findById(Long id) {
    return data.stream().filter(item -> item.getId().equals(id)).findAny();
  }

  @Override
  public Complaint save(Complaint complaint) {
    if (complaint.getId() == null || complaint.getId() == 0L) {
      autoIncreasingCount++;
      Complaint newComplaint = Complaint.builder()
          .id(autoIncreasingCount)
          .requester(complaint.getRequester())
          .processor(complaint.getProcessor())
          .complaintType(complaint.getComplaintType())
          .content(complaint.getContent())
          .processType(complaint.getProcessType())
          .reply(complaint.getReply())
          .build();
      data.add(newComplaint);
      return newComplaint;
    } else {
      data.removeIf(item -> item.getId().equals(complaint.getId()));
      Complaint newComplaint = Complaint.builder()
          .id(complaint.getId())
          .requester(complaint.getRequester())
          .processor(complaint.getProcessor())
          .complaintType(complaint.getComplaintType())
          .content(complaint.getContent())
          .processType(complaint.getProcessType())
          .reply(complaint.getReply())
          .build();
      data.add(newComplaint);
      return newComplaint;
    }
  }

  @Override
  public void delete(Complaint complaint) {
    data.removeIf(item -> item.getId().equals(complaint.getId()));
  }
}
