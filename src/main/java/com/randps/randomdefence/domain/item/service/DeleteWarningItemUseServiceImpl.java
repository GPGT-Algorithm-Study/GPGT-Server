package com.randps.randomdefence.domain.item.service;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.ItemRepository;
import com.randps.randomdefence.domain.item.domain.UserItemRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DeleteWarningItemUseServiceImpl extends ItemUseService {

    protected DeleteWarningItemUseServiceImpl(UserRepository userRepository, ItemRepository itemRepository, UserItemRepository userItemRepository) {
        super(userRepository, itemRepository, userItemRepository);
    }

    @Transactional
    @Override
    public Object itemEffect(User user, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

        // 에러 처리
        if (!item.getName().equals("경고 차감권")) {
            throw new IllegalArgumentException("잘못된 아이템 사용입니다.");
        }
        if (user.getWarning() == 0) {
            throw new IllegalArgumentException("경고가 없으면 아이템을 사용할 수 없습니다.");
        }

        // 경고를 차감하고 저장한다.
        user.decreaseWarning();
        userRepository.save(user);

        return null;
    }
}
