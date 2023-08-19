package com.randps.randomdefence.domain.item.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ItemDto {
    public Long id;

    public String name;

    public String description;

    public Integer itemValue;

    public Integer maxItemCount;

    @Builder
    public ItemDto(Long id, String name, String description, Integer itemValue, Integer maxItemCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemValue = itemValue;
        this.maxItemCount = maxItemCount;
    }
}
