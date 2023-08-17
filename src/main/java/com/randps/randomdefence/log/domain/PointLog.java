package com.randps.randomdefence.log.domain;


import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_POINT_LOG")
@Entity
public class PointLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private Integer changedValue;

    private String description;

    private Boolean state;

    @Builder
    public PointLog(String bojHandle, Integer changedValue, String description, Boolean state) {
        this.bojHandle = bojHandle;
        this.changedValue = changedValue;
        this.description = description;
        this.state = state;
    }

    public void setStateOk() {
        this.state = true;
    }

    public void setStateNo() {
        this.state = false;
    }

}
