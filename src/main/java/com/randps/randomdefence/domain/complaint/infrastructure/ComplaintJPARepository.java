package com.randps.randomdefence.domain.complaint.infrastructure;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintJPARepository extends JpaRepository<Complaint, Long> {

  List<Complaint> findAllByOrderByIdDesc();

  List<Complaint> findAllByRequester(String requester);

  List<Complaint> findAllByProcessor(String processor);

  List<Complaint> findAllByComplaintType(ComplaintType complaintType);

  List<Complaint> findAllByProcessType(ProcessType processType);

}
