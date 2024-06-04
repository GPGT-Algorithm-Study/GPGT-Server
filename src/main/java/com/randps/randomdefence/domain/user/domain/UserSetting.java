package com.randps.randomdefence.domain.user.domain;


import com.randps.randomdefence.global.auditing.BaseTimeEntity;
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
@Table(name = "RD_USER_SETTING")
@Entity
public class UserSetting extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String bojHandle;

  private Boolean scrapingOn;

  private Boolean warningOn;

  @Builder
  public UserSetting(Long id, String bojHandle, Boolean scrapingOn, Boolean warningOn) {
    this.id = id;
    this.bojHandle = bojHandle;
    this.scrapingOn = scrapingOn;
    this.warningOn = warningOn;
  }

  public void toggleScraping() {
    this.scrapingOn = !this.scrapingOn;
  }

  public void toggleWarning() {
    this.warningOn = !this.warningOn;
  }

  public void disableWarning() {
    this.warningOn = false;
  }

  public void enableWarning() {
    this.warningOn = true;
  }

  public void disableScraping() {
    this.scrapingOn = false;
  }

  public void enableScraping() {
    this.scrapingOn = true;
  }

}
