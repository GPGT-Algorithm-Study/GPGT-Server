package com.randps.randomdefence.domain.item.infrastructure;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.service.port.ItemRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryAdapter implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;

    @Override
    public Item save(Item item) {
        return itemJpaRepository.save(item);
    }

    @Override
    public List<Item> findAll() {
        return itemJpaRepository.findAll();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemJpaRepository.findById(id);
    }

    @Override
    public Item getReferenceById(Long itemId) {
        return itemJpaRepository.getReferenceById(itemId);
    }
}
