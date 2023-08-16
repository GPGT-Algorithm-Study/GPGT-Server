package com.randps.randomdefence.item.controller;

import com.randps.randomdefence.item.dto.ItemDto;
import com.randps.randomdefence.item.dto.UserItemResponse;
import com.randps.randomdefence.item.service.ItemSaveService;
import com.randps.randomdefence.item.service.ItemSearchService;
import com.randps.randomdefence.item.service.TestItemUseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private final TestItemUseServiceImpl testItemUseService;

    private final ItemSaveService itemSaveService;

    private final ItemSearchService itemSearchService;

    /*
     * 유저의 모든 아이템 목록을 반환한다.
     */
    @GetMapping("/user")
    public List<UserItemResponse> findUserAllItem(@Param("bojHandle") String bojHandle) {
        List<UserItemResponse> items = itemSearchService.findAllUserItem(bojHandle);

        return items;
    }

    /*
     * 모든 아이템 목록을 반환한다.
     */
    @GetMapping("/all")
    public List<ItemDto> findAllItem() {
        List<ItemDto> items = itemSearchService.findAllItem();

        return items;
    }

    /*
     * 아이템을 사용한다. (아이템에 따라 다른 서비스 호출)
     */
    @PutMapping("/use")
    public HttpStatus useItem(@Param("bojHandle") String bojHandle, @Param("itemId") Long itemId) {
        Boolean resultState = testItemUseService.useItem(bojHandle, itemId);

        if (resultState)
            return HttpStatus.ACCEPTED;
        else
            return HttpStatus.NOT_ACCEPTABLE;
    }

    /*
     * 아이템을 구매한다.
     */
    @PostMapping("/buy")
    public HttpStatus buyItem(@Param("bojHandle") String bojHandle, @Param("itemId") Long itemId) {
        itemSaveService.buyItem(bojHandle, itemId);

        return HttpStatus.ACCEPTED;
    }

    /*
     * 테스트 아이템 생성 (테스트)
     */
    @PostMapping("/make-test-item")
    public HttpStatus makeTestItem() {
        itemSaveService.makeItem();

        return HttpStatus.ACCEPTED;
    }
}
