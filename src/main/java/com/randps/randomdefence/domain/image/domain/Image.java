package com.randps.randomdefence.domain.image.domain;

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
@Table(name = "RD_IMAGE")
@Entity
public class Image extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String originName;

    private String contentType;

    private Boolean state; // {false : 이미지 저장 미확정, true : 이미지 저장 확정}

    @Builder
    public Image(Long id, String url, String originName, String contentType) {
        this.id = id;
        this.url = url;
        this.originName = originName;
        this.contentType = contentType;
        this.state = false;
    }

    public void setStateOk() {
        this.state = true;
    }
}
