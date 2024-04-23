package com.randps.randomdefence.domain.notify.domain;

import com.randps.randomdefence.domain.notify.enums.NotifyType;
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

@Getter
@Entity
@NoArgsConstructor
@Table(name = "RD_NOTIFY")
public class Notify extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String receiver;

  @Column(length = 2000)
  private String message;

  private Long linkedBoardId;

  private NotifyType type;

  private Boolean isRead;


  @Builder
  public Notify(Long id, String receiver, Long linkedBoardId, String message, NotifyType type) {
    this.id = id;
    this.receiver = receiver;
    this.message = message;
    this.linkedBoardId = linkedBoardId;
    this.type = type;
    this.isRead = false;
  }

  public void updateMessage(String message) {
    this.message = message;
  }

  public void linking(Long linkedBoardId) {
    this.linkedBoardId = linkedBoardId;
  }

  public void updateType(NotifyType type) {
    this.type = type;
  }

  public void read() {
    this.isRead = true;
  }

}
