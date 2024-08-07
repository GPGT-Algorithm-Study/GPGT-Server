package com.randps.randomdefence.domain.item.controller;

import com.randps.randomdefence.domain.item.dto.ItemDto;
import com.randps.randomdefence.domain.item.dto.UserItemResponse;
import com.randps.randomdefence.domain.item.service.BoolshitItemUseServiceImpl;
import com.randps.randomdefence.domain.item.service.DeleteWarningItemUseServiceImpl;
import com.randps.randomdefence.domain.item.service.ItemSaveService;
import com.randps.randomdefence.domain.item.service.ItemSearchService;
import com.randps.randomdefence.domain.item.service.TestItemUseServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private final TestItemUseServiceImpl testItemUseService;

    private final BoolshitItemUseServiceImpl boolshitItemUseService;

    private final DeleteWarningItemUseServiceImpl deleteWarningItemUseService;

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
    public ResponseEntity<Map<String, String>> useItem(@Param("bojHandle") String bojHandle, @Param("itemId") Long itemId, @Param("message") String message) {
        Boolean resultState = false;

        if (itemId == 1) {
            // 테스트 아이템 사용
            resultState = testItemUseService.useItem(bojHandle, itemId);
        }
        if (itemId == 2) {
            // 경고 차감권 사용
            resultState = deleteWarningItemUseService.useItem(bojHandle, itemId);
        }
        if (itemId == 3) {
            // 랜덤 스트릭 프리즈 사용 (하지만 사실 유저가 직접 사용하면 안됨)
            resultState = false;
        }
        if (itemId == 4) {
            // 나의 한마디 사용
            boolshitItemUseService.setMessage(message);
            resultState = boolshitItemUseService.useItem(bojHandle, itemId);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        Map<String, String> map = new HashMap<>();
        if (resultState) {
            HttpStatus httpStatus = HttpStatus.OK;

            map.put("type", httpStatus.getReasonPhrase());
            map.put("code", "200");
            map.put("message", "요청을 성공했습니다.");
            return new ResponseEntity<>(map, responseHeaders, httpStatus);
        }
        else {
            HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;

            map.put("type", httpStatus.getReasonPhrase());
            map.put("code", "200");
            map.put("message", "요청에 실패했습니다.");
            return new ResponseEntity<>(map, responseHeaders, httpStatus);
        }
    }

    /*
     * 아이템을 구매한다.
     */
    @PostMapping("/buy")
    public ResponseEntity<Map<String, String>> buyItem(@Param("bojHandle") String bojHandle, @Param("itemId") Long itemId) {
        itemSaveService.buyItem(bojHandle, itemId);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 테스트 아이템 생성 (테스트)
     */
    @PostMapping("/make-item")
    public ResponseEntity<Map<String, String>> makeItem() {
        itemSaveService.makeItem();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
