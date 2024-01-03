package com.randps.randomdefence.domain.item.mock;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.service.port.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeItemRepository implements ItemRepository {

    private final List<Item> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public Item save(Item item) {
        if (item.getId() == null || item.getId() == 0L) {
            autoIncreasingCount++;
            Item newItem = Item.builder()
                    .id(autoIncreasingCount)
                    .name(item.getName())
                    .description(item.getDescription())
                    .itemValue(item.getItemValue())
                    .maxItemCount(item.getMaxItemCount())
                    .build();
            data.add(newItem);
            return newItem;
        } else {
            data.removeIf(elem -> elem.getId().equals(item.getId()));
            Item newItem = Item.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .description(item.getDescription())
                    .itemValue(item.getItemValue())
                    .maxItemCount(item.getMaxItemCount())
                    .build();
            data.add(newItem);
            return newItem;
        }
    }

    @Override
    public List<Item> findAll() {
        return data;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return data.stream().filter(elem -> elem.getId().equals(id)).findAny();
    }

    @Override
    public Item getReferenceById(Long itemId) {
        return data.stream().filter(elem -> elem.getId().equals(itemId)).findAny().get();
    }
}
