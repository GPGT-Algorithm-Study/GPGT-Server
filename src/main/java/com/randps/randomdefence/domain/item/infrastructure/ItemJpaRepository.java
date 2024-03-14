package com.randps.randomdefence.domain.item.infrastructure;

import com.randps.randomdefence.domain.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {
}
