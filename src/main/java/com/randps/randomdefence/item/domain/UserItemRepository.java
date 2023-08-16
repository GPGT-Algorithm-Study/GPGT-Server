package com.randps.randomdefence.item.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    List<UserItem> findAllByBojHandle(String bojHandle);

    Optional<UserItem> findByBojHandleAndItem(String bojHandle, Item item);
}
