package com.randps.randomdefence.domain.item.service;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.UserItem;
import com.randps.randomdefence.domain.item.service.port.ItemRepository;
import com.randps.randomdefence.domain.item.service.port.UserItemRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;

public abstract class ItemUseService {

    protected final UserRepository userRepository;

    protected final ItemRepository itemRepository;

    protected final UserItemRepository userItemRepository;

    protected ItemUseService(UserRepository userRepository, ItemRepository itemRepository,
                             UserItemRepository userItemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userItemRepository = userItemRepository;
    }

    /*
     * 유저가 아이템을 사용한다.
     */
    @Transactional
    public Boolean useItem(String bojHandle, Long itemId) {
        User user = userRepository.findByBojHandle(bojHandle)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));
        Optional<UserItem> userItem = userItemRepository.findByBojHandleAndItem(bojHandle, item);

        // 아이템이 없다면 사용에 실패한다.
        if (userItem.isEmpty()) {
            return false;
        }

        // 아이템이 있다면 사용한다.
        itemEffect(user, itemId);

        // 아이템의 개수가 1개라면 사용하고 삭제한다.
        if (userItem.get().getCount() == 1) {
            userItemRepository.delete(userItem.get());
            return true;
        }
        // 아이템의 개수가 1개 이상이라면 하나 사용한다.
        userItem.get().decreaseCount();

        // 유저 아이템의 상태를 저장한다.
        userItemRepository.save(userItem.get());

        return true;
    }

    /*
     * 유저가 아이템을 사용한다.
     */
    @Transactional
    public Boolean useItem(User user, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));
        Optional<UserItem> userItem = userItemRepository.findByBojHandleAndItem(user.getBojHandle(), item);

        // 아이템이 없다면 사용에 실패한다.
        if (userItem.isEmpty()) {
            return false;
        }

        // 아이템이 있다면 사용한다.
        itemEffect(user, itemId);

        // 아이템의 개수가 1개라면 사용하고 삭제한다.
        if (userItem.get().getCount() == 1) {
            userItemRepository.delete(userItem.get());
            return true;
        }
        // 아이템의 개수가 1개 이상이라면 하나 사용한다.
        userItem.get().decreaseCount();

        // 유저 아이템의 상태를 저장한다.
        userItemRepository.save(userItem.get());

        return true;
    }

    /*
     * 아이템의 효과를 실행시킨다.
     */
    @Transactional
    abstract public Object itemEffect(User user, Long itemId);
}
