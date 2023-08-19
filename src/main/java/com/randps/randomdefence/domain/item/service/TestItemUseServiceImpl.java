package com.randps.randomdefence.domain.item.service;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.ItemRepository;
import com.randps.randomdefence.domain.item.domain.UserItemRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TestItemUseServiceImpl extends ItemUseService{

    public TestItemUseServiceImpl(UserRepository userRepository, ItemRepository itemRepository, UserItemRepository userItemRepository) {
        super(userRepository, itemRepository, userItemRepository);
    }

    /*
     * 아이템의 효과를 실행시킨다. (기능 테스트용)
     */
    @Transactional
    @Override
    public Object itemEffect(String bojHandle, Long itemId) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

        System.out.println("아이템을 정상적으로 사용했습니다.");
        return true;
    }
}
