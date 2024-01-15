package com.randps.randomdefence.domain.item.infrastructure;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.UserItem;
import com.randps.randomdefence.domain.item.service.port.UserItemRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserItemRepositoryAdapter implements UserItemRepository {

    private final UserItemJpaRepository userItemJpaRepository;

    @Override
    public List<UserItem> findAllByBojHandle(String bojHandle) {
        return userItemJpaRepository.findAllByBojHandle(bojHandle);
    }

    @Override
    public Optional<UserItem> findByBojHandleAndItem(String bojHandle, Item item) {
        return userItemJpaRepository.findByBojHandleAndItem(bojHandle, item);
    }

    @Override
    public UserItem save(UserItem userItem) {
        return userItemJpaRepository.save(userItem);
    }

    @Override
    public void delete(UserItem userItem) {
        userItemJpaRepository.delete(userItem);
    }

    @Override
    public void deleteAllByBojHandle(String bojHandle) {
        userItemJpaRepository.deleteAllByBojHandle(bojHandle);
    }
}
