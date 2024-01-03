package com.randps.randomdefence.domain.item.service.port;

import com.randps.randomdefence.domain.item.domain.Item;
import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item save(Item item);

    List<Item> findAll();

    Optional<Item> findById(Long id);

    Item getReferenceById(Long itemId);
}
