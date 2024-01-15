package com.randps.randomdefence.domain.item.mock;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.UserItem;
import com.randps.randomdefence.domain.item.service.port.UserItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeUserItemRepository implements UserItemRepository {

    private final List<UserItem> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public List<UserItem> findAllByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserItem> findByBojHandleAndItem(String bojHandle, Item item) {
        return data.stream()
                .filter(elem -> elem.getBojHandle().equals(bojHandle) && elem.getItem().getId().equals(item.getId()))
                .findAny();
    }

    @Override
    public UserItem save(UserItem userItem) {
        if (userItem.getId() == null || userItem.getId() == 0L) {
            autoIncreasingCount++;
            UserItem newUserItem = UserItem.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(userItem.getBojHandle())
                    .count(userItem.getCount())
                    .item(userItem.getItem())
                    .build();
            data.add(newUserItem);
            return newUserItem;
        } else {
            data.removeIf(item -> item.getId().equals(userItem.getId()));
            UserItem newUserItem = UserItem.builder()
                    .id(userItem.getId())
                    .bojHandle(userItem.getBojHandle())
                    .count(userItem.getCount())
                    .item(userItem.getItem())
                    .build();
            data.add(newUserItem);
            return newUserItem;
        }
    }

    @Override
    public void delete(UserItem userItem) {
        data.removeIf(item -> item.getId().equals(userItem.getId()));
    }

    @Override
    public void deleteAllByBojHandle(String bojHandle) {
        data.removeIf(item -> item.getBojHandle().equals(bojHandle));
    }

}
