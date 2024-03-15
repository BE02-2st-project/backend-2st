package com.github.super_mall.controller.itemController;

import com.github.super_mall.dto.itemDto.ItemAdditionalDto;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.userDetailEntity.CustomUserDetails;
import com.github.super_mall.service.itemService.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.security.Principal;

@Tag(name = "Example", description = "Example API")
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

    @GetMapping("/search")
    public List<Item> findItemsByNameKeyword(@RequestParam("keyword") String keyword) {
        return itemService.findByNameContaining(keyword);
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody ItemAdditionalDto addItem,@AuthenticationPrincipal CustomUserDetails user) {
        itemService.addItem(addItem, user.getEmail());
        return ResponseEntity.ok("상품이 등록되었습니다.");
    }


}
