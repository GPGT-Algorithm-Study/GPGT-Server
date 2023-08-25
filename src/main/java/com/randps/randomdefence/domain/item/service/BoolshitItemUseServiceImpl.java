package com.randps.randomdefence.domain.item.service;

import com.randps.randomdefence.domain.boolshit.service.BoolshitService;
import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.ItemRepository;
import com.randps.randomdefence.domain.item.domain.UserItemRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BoolshitItemUseServiceImpl extends ItemUseService {

    private String message = "";

    private final BoolshitService boolshitService;

    protected BoolshitItemUseServiceImpl(UserRepository userRepository, ItemRepository itemRepository, UserItemRepository userItemRepository, BoolshitService boolshitService) {
        super(userRepository, itemRepository, userItemRepository);
        this.boolshitService = boolshitService;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Object itemEffect(User user, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

        // 에러 처리
        if (!item.getName().equals("나의 한마디")) {
            throw new IllegalArgumentException("잘못된 아이템 사용입니다.");
        }

        // 나의 한마디 추가
        boolshitService.add(this.message, user.getBojHandle());

        return null;
    }
}
