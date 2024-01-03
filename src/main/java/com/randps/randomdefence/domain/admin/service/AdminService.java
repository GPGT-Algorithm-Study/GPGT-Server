package com.randps.randomdefence.domain.admin.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.dto.ItemDto;
import com.randps.randomdefence.domain.item.service.port.ItemRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final ItemRepository itemRepository;

    /**
     * 특정 아이템Id로 아이템 가격 변경
     */
    @Transactional
    public ItemDto updateCost(Long itemId, Integer price) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("존재하지 않는 아이템입니다."));

        if (price < 0) {
            throw new IllegalArgumentException("0원 미만의 가격은 설정할 수 없습니다.");
        }

        item.updatePrice(price);
        itemRepository.save(item);

        return item.toDto();
    }

}
