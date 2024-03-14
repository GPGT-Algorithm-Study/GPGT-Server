package com.randps.randomdefence.domain.item.service;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.UserItem;
import com.randps.randomdefence.domain.item.service.port.ItemRepository;
import com.randps.randomdefence.domain.item.service.port.UserItemRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserGrass;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.service.UserGrassService;
import com.randps.randomdefence.domain.user.service.port.UserGrassRepository;
import com.randps.randomdefence.domain.user.service.port.UserRandomStreakRepository;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
public class RandomStreakFreezeItemUseServiceImpl extends ItemUseService {

    private final UserRandomStreakRepository userRandomStreakRepository;

    private final UserGrassService userGrassService;

    private final UserGrassRepository userGrassRepository;

    @Builder
    protected RandomStreakFreezeItemUseServiceImpl(UserRepository userRepository, ItemRepository itemRepository,
                                                   UserItemRepository userItemRepository,
                                                   UserRandomStreakRepository userRandomStreakRepository,
                                                   UserGrassService userGrassService,
                                                   UserGrassRepository userGrassRepository) {
        super(userRepository, itemRepository, userItemRepository);
        this.userRandomStreakRepository = userRandomStreakRepository;
        this.userGrassService = userGrassService;
        this.userGrassRepository = userGrassRepository;
    }

    /*
     * 스트릭 프리즈가 있는지 체크한다.
     */
    @Transactional
    public Boolean isExist(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Item item = itemRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));
        Optional<UserItem> userItem = userItemRepository.findByBojHandleAndItem(bojHandle, item);

        // 아이템이 없다면 false를 반환
        return userItem.isPresent();
        // 아이템이 있다면 true를 반환
    }

    /*
     * 스트릭 프리즈가 있는지 체크한다.
     */
    @Transactional
    public Boolean isExist(User user) {
        Item item = itemRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));
        Optional<UserItem> userItem = userItemRepository.findByBojHandleAndItem(user.getBojHandle(), item);

        // 아이템이 없다면 false를 반환
        return userItem.isPresent();
        // 아이템이 있다면 true를 반환
    }

    @Transactional
    @Override
    public Object itemEffect(User user, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

        // 에러 처리
        if (!item.getName().equals("랜덤 스트릭 프리즈")) {
            throw new IllegalArgumentException("잘못된 아이템 사용입니다.");
        }

        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(user.getBojHandle())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스트릭입니다."));
        // 유저의 오늘 문제를 푼 상태가 true라면 아이템을 사용하지 않는다.
        if (userRandomStreak.getIsTodayRandomSolved()) {
            throw new IllegalArgumentException("이미 문제를 푼 상태입니다.");
        }

        // 유저 잔디를 풀었다고 체크하고 문제 번호를 0으로 만든다. 그 뒤 저장한다.
        UserGrass userGrass = userGrassService.findYesterdayUserGrass(userRandomStreak);
        userGrass.infoCheckOk();
        userGrass.setProblemId(0);
        userGrassRepository.save(userGrass);

        return true;
    }
}
