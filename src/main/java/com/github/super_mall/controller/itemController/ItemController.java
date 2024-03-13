package com.github.super_mall.controller.itemController;

import com.github.super_mall.dto.itemDto.ItemAdditionalDto;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.service.itemService.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> findAllItem() {
        return itemService.findAllItem();
    }

    @GetMapping("/search") // postman X
    public List<Item> findItemsByNameKeyword(@RequestParam("keyword") String keyword) {
        return itemService.findByNameContaining(keyword);
    }

    @PostMapping // postman X
    public ResponseEntity<?> addItem(@RequestBody ItemAdditionalDto addItem) {
        itemService.addItem(addItem);
        return ResponseEntity.ok("상품이 등록되었습니다!");
    }


}
