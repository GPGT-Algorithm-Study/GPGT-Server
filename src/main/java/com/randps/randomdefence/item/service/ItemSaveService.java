package com.randps.randomdefence.item.service;

import com.randps.randomdefence.item.domain.Item;
import com.randps.randomdefence.item.domain.ItemRepository;
import com.randps.randomdefence.item.domain.UserItem;
import com.randps.randomdefence.item.domain.UserItemRepository;
import com.randps.randomdefence.user.domain.User;
import com.randps.randomdefence.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemSaveService {

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final UserItemRepository userItemRepository;

    /*
     * 유저가 아이템을 구매한다.
     */
    @Transactional
    public Boolean buyItem(String bojHandle, Long itemId) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));
        Optional<UserItem> userItem = userItemRepository.findByBojHandleAndItem(bojHandle, item);

        // 아이템이 하나라도 있는 경우
        if (userItem.isPresent()) {
            // 포인트를 지불한다.
            if (!user.decreasePoint(userItem.get().getItem().getItemValue())) {
                // 포인트가 부족하면 구매가 실패한다.
                throw new IllegalArgumentException("구매 실패 : 포인트가 부족합니다.");
//                return false;
            }

            // 최대 개수를 확인한다.
            if (!userItem.get().increaseCount()) {
                // 최대 개수를 넘으면 구매가 실패한다.
                // 포인트를 되돌린다.
                user.increasePoint(userItem.get().getItem().getItemValue());
                userRepository.save(user);
                throw new IllegalArgumentException("구매 실패 : 가질 수 있는 아이템의 최대 개수를 초과했습니다.");
//                return false;
            }

            // 상태 변화를 저장한다.
            userRepository.save(user);
            userItemRepository.save(userItem.get());

            return true;
        }
        // 아이템이 하나도 없는 경우
        else {
            // 포인트를 지불한다.
            if (!user.decreasePoint(item.getItemValue())) {
                // 포인트가 부족하면 구매가 실패한다.
                throw new IllegalArgumentException("구매 실패 : 포인트가 부족합니다.");
//                return false;
            }

            // 아이템을 생성한다.
            UserItem newUserItem = UserItem.builder()
                    .bojHandle(bojHandle)
                    .count(1)
                    .item(itemRepository.getReferenceById(itemId))
                    .build();

            // 상태 변화를 저장한다.
            userRepository.save(user);
            userItemRepository.save(newUserItem);

            return true;
        }
    }

    /*
     * 테스트 아이템을 생성한다. (테스트)
     */
    @Transactional
    public Boolean makeItem() {
        Item newItem = Item.builder()
                .name("테스트 상품")
                .description("테스트 상품입니다. 사용하면 서버에 로그를 하나 찍습니다.")
                .itemValue(1)
                .maxItemCount(2)
                .build();
        itemRepository.save(newItem);
        return true;
    }
}
