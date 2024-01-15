package com.randps.randomdefence.domain.item.infrastructure;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.UserItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemJpaRepository extends JpaRepository<UserItem, Long> {
    List<UserItem> findAllByBojHandle(String bojHandle);

    Optional<UserItem> findByBojHandleAndItem(String bojHandle, Item item);

    void deleteAllByBojHandle(String bojHandle);
}
