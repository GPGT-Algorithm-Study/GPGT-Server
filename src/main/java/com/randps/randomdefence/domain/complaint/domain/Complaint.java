package com.randps.randomdefence.domain.complaint.domain;

import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "RD_COMPLAINT")
@Entity
public class Complaint extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String requester; // 요청자의 bojHandle

  private String content;

  private ComplaintType complaintType;

  private ProcessType processType;

  private String processor; // 처리자의 bojHandle

  @Column(columnDefinition = "LONGTEXT")
  private String reply;

  @Builder
  public Complaint(Long id, String requester, String content, ComplaintType complaintType, ProcessType processType, String processor, String reply) {
      this.id = id;
      this.requester = requester;
      this.content = content;
      this.complaintType = complaintType;
      this.processType = processType;
      this.processor = processor;
      this.reply = reply;
  }

  public void updateContentAndType(String content, ComplaintType complaintType) {
    this.content = content;
    this.complaintType = complaintType;
  }

  public void updateProcessorAndReply(String processor, String reply) {
    this.processor = processor;
    this.reply = reply;
  }

  public void setProcessWAITING() {
    this.processType = ProcessType.WAITING;
  }

  public void setProcessPROCESSING() {
    this.processType = ProcessType.PROCESSING;
  }

  public void setProcessDONE() {
    this.processType = ProcessType.DONE;
  }


}
