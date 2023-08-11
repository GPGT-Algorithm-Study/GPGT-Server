package com.randps.randomdefence.recommendation.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TitleDto {
    public String language;
    public String languageDisplayName;
    public String title;
    public Boolean isOriginal;

    @Builder
    public TitleDto(String language, String languageDisplayName, String title, Boolean isOriginal) {
        this.language = language;
        this.languageDisplayName = languageDisplayName;
        this.title = title;
        this.isOriginal = isOriginal;
    }
}
