package com.randps.randomdefence.domain.complaint.infrastructure;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintJPARepository extends JpaRepository<Complaint, Long> {

  List<Complaint> findAllByOrderByIdDesc();

  List<Complaint> findAllByRequesterOrderByIdDesc(String requester);

  List<Complaint> findAllByProcessorOrderByIdDesc(String processor);

  List<Complaint> findAllByComplaintTypeOrderByIdDesc(ComplaintType complaintType);

  List<Complaint> findAllByProcessTypeOrderByIdDesc(ProcessType processType);

}
