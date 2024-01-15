package com.randps.randomdefence.domain.item.service;

import com.randps.randomdefence.domain.item.domain.Item;
import com.randps.randomdefence.domain.item.domain.UserItem;
import com.randps.randomdefence.domain.item.dto.ItemDto;
import com.randps.randomdefence.domain.item.dto.UserItemResponse;
import com.randps.randomdefence.domain.item.service.port.ItemRepository;
import com.randps.randomdefence.domain.item.service.port.UserItemRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemSearchService {

    private final ItemRepository itemRepository;

    private final UserItemRepository userItemRepository;

    /*
     * 유저의 모든 아이템을 반환한다.
     */
    @Transactional
    public List<UserItemResponse> findAllUserItem(String bojHandle) {
        List<UserItem> userItems = userItemRepository.findAllByBojHandle(bojHandle);
        List<UserItemResponse> userItemResponses = new ArrayList<>();

        for (UserItem userItem : userItems) {
            UserItemResponse userItemResponse = UserItemResponse.builder()
                    .bojHandle(userItem.getBojHandle())
                    .count(userItem.getCount())
                    .item(userItem.getItem())
                    .build();

            userItemResponses.add(userItemResponse);
        }

        return userItemResponses;
    }

    /*
     * 모든 아이템 리스트를 반환한다.
     */
    @Transactional
    public List<ItemDto> findAllItem() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> itemDtos = new ArrayList<>();

        for (Item item : items) {
            itemDtos.add(item.toDto());
        }

        return itemDtos;
    }

}
