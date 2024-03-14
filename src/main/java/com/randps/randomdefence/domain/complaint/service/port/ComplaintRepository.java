package com.randps.randomdefence.domain.complaint.service.port;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import java.util.List;
import java.util.Optional;

public interface ComplaintRepository {

    List<Complaint> findAll();

    List<Complaint> findAllByOrderByIdDesc();

    List<Complaint> findAllByRequester(String requester);

    List<Complaint> findAllByProcessor(String processor);

    List<Complaint> findAllByComplaintType(ComplaintType complaintType);

    List<Complaint> findAllByProcessType(ProcessType processType);

    Optional<Complaint> findById(Long id);

    Complaint save(Complaint complaint);

    void delete(Complaint complaint);


}
