package com.randps.randomdefence.domain.complaint.dto;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ComplaintSaveRequest {

    private String requester;

    private String content;

    private ComplaintType complaintType;


    public static Complaint to(ComplaintSaveRequest request) {
        return Complaint.builder()
            .requester(request.getRequester())
            .content(request.getContent())
            .complaintType(request.getComplaintType())
            .processType(ProcessType.WAITING)
            .processor(null)
            .build();
    }

    public static List<Complaint> to(List<ComplaintSaveRequest> requests) {
        return requests.stream().map(ComplaintSaveRequest::to).collect(Collectors.toList());
    }

}
