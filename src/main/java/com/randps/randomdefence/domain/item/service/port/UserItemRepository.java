package com.randps.randomdefence.domain.item.service.port;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.UserItem;
import java.util.List;
import java.util.Optional;

public interface UserItemRepository {

    List<UserItem> findAllByBojHandle(String bojHandle);

    Optional<UserItem> findByBojHandleAndItem(String bojHandle, Item item);

    UserItem save(UserItem userItem);

    void delete(UserItem userItem);

}
