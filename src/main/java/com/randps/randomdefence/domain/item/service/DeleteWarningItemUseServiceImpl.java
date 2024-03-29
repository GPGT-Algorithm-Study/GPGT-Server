package com.randps.randomdefence.domain.item.service;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.service.port.ItemRepository;
import com.randps.randomdefence.domain.item.service.port.UserItemRepository;
import com.randps.randomdefence.domain.log.service.WarningLogSaveService;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeleteWarningItemUseServiceImpl extends ItemUseService {

    private final WarningLogSaveService warningLogSaveService;

    protected DeleteWarningItemUseServiceImpl(UserRepository userRepository, ItemRepository itemRepository,
                                              UserItemRepository userItemRepository,
                                              WarningLogSaveService warningLogSaveService) {
        super(userRepository, itemRepository, userItemRepository);
        this.warningLogSaveService = warningLogSaveService;
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
        boolean isSuccess = user.decreaseWarning();
        // 경고 로그를 저장한다.
        if (isSuccess) {
            warningLogSaveService.saveWarningLog(user.getBojHandle(), -1,
                    "[" + user.getBojHandle() + "]" + "'s warnings decreased by 1" + " - 사유: 경고 차감 아이템 사용 " + "[" + (
                            user.getWarning() + 1) + "->" + user.getWarning() + "]", true);
        }

        userRepository.save(user);

        return null;
    }
}
