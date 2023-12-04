package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.UUID;
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
@Table(name = "RD_USER_REGISTER")
@Entity
public class UserRegister extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private String code;

    private Boolean valid;

    @Builder
    public UserRegister(String bojHandle) {
        this.bojHandle = bojHandle;
        this.code = UUID.randomUUID().toString();
        this.valid = false;
    }

    public Boolean isValid() {
        return this.valid;
    }

    public void setValid() {
        this.valid = true;
    }

    public void makeRandomCode() {
        this.code = UUID.randomUUID().toString();
    }

    // 코드 발급 3분이 지났으면 Timeout
    public Boolean isTimeOut() {
        LocalDateTime makeTime = this.getCreatedDate();
        return makeTime.isBefore(LocalDateTime.now().minusMinutes(3));
    }

}
