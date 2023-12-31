package com.randps.randomdefence.domain.item.service;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.UserItem;
import com.randps.randomdefence.domain.item.service.port.ItemRepository;
import com.randps.randomdefence.domain.item.service.port.UserItemRepository;
import com.randps.randomdefence.domain.log.service.PointLogSaveService;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemSaveService {

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final UserItemRepository userItemRepository;

    private final PointLogSaveService pointLogSaveService;

    /*
     * 유저가 아이템을 구매한다.
     */
    @Transactional
    public Boolean buyItem(String bojHandle, Long itemId) {
        User user = userRepository.findByBojHandle(bojHandle)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));
        Optional<UserItem> userItem = userItemRepository.findByBojHandleAndItem(bojHandle, item);

        // 아이템이 하나라도 있는 경우
        if (userItem.isPresent()) {
            // 포인트를 지불한다.
            if (!user.decreasePoint(userItem.get().getItem().getItemValue())) {
                // 포인트가 부족하면 구매가 실패한다.
                throw new ArithmeticException("구매 실패 : 포인트가 부족합니다.");
//                return false;
            }

            // 최대 개수를 확인한다.
            if (!userItem.get().increaseCount()) {
                // 최대 개수를 넘으면 구매가 실패한다.
                // 포인트를 되돌린다.
                user.increasePoint(userItem.get().getItem().getItemValue());
                userRepository.save(user);
                throw new ArithmeticException("구매 실패 : 가질 수 있는 아이템의 최대 개수를 초과했습니다.");
//                return false;
            }

            // 구매 로그를 작성한다.
            pointLogSaveService.savePointLog(bojHandle, -item.getItemValue(),
                    "-" + item.getItemValue() + " points, purchase \'" + item.getName() + "\'", true);

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
                throw new ArithmeticException("구매 실패 : 포인트가 부족합니다.");
//                return false;
            }

            // 아이템을 생성한다.
            UserItem newUserItem = UserItem.builder().bojHandle(bojHandle).count(1)
                    .item(itemRepository.getReferenceById(itemId)).build();

            // 구매 로그를 작성한다.
            pointLogSaveService.savePointLog(bojHandle, -item.getItemValue(),
                    "-" + item.getItemValue() + " points, purchase \'" + item.getName() + "\'", true);

            // 상태 변화를 저장한다.
            userRepository.save(user);
            userItemRepository.save(newUserItem);

            return true;
        }
    }

    /*
     * 모든 아이템을 생성한다.
     */
    @Transactional
    public Boolean makeItem() {
        List<Item> items = itemRepository.findAll();

        if (!items.isEmpty()) {
            throw new IllegalArgumentException("이미 아이템이 생성되었습니다.");
        }

        // 테스트 상품
        Item newItem = Item.builder().name("테스트 상품").description("테스트 상품입니다. 사용하면 서버에 로그를 하나 찍습니다.").itemValue(1)
                .maxItemCount(2).build();
        itemRepository.save(newItem);

        // 경고 차감권
        Item deleteWarningItem = Item.builder().name("경고 차감권").description("사용하면 경고를 하나 차감합니다.").itemValue(40)
                .maxItemCount(3).build();
        itemRepository.save(deleteWarningItem);

        // 랜덤 스트릭 프리즈
        Item randomStreakFreezeItem = Item.builder().name("랜덤 스트릭 프리즈")
                .description("가지고 있으면 문제를 풀지 않았을 시, 자동으로 스트릭이 유지됩니다.").itemValue(200).maxItemCount(2).build();
        itemRepository.save(randomStreakFreezeItem);

        // 나의 한마디
        Item boolshitItem = Item.builder().name("나의 한마디").description("나의 멋진 한마디를 모두에게 공지합니다.").itemValue(1)
                .maxItemCount(1).build();
        itemRepository.save(boolshitItem);

        return true;
    }
}
