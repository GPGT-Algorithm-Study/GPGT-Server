package com.randps.randomdefence.item.dto;

import com.randps.randomdefence.item.domain.Item;
import lombok.Builder;
import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class UserItemResponse {
    public String bojHandle;

    public Integer count;

    public ItemDto item;

    @Builder
    public UserItemResponse(String bojHandle, Integer count, Item item) {
        this.bojHandle = bojHandle;
        this.count = count;
        this.item = item.toDto();
    }
}
