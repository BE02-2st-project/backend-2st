package com.github.super_mall.controller.itemController;

import com.github.super_mall.dto.itemDto.ItemRegisterDto;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.saleEntity.Sale;
import com.github.super_mall.entity.userDetailEntity.CustomUserDetails;
import com.github.super_mall.service.itemService.ItemService;
import com.github.super_mall.service.saleService.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final SaleService saleService;


    //pageable
    @GetMapping("/page")
    public Page<Item> findAllItem(Pageable pageable) {
        return itemService.findWithPaging(pageable);
    }

    @GetMapping
    public List<Item> findAllItem() {
        return itemService.findAllItem();
    }

    @GetMapping("/search")
    public List<Item> findItemsByNameKeyword(@RequestParam("keyword") String keyword) {
        return itemService.findByNameContaining(keyword);
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody ItemRegisterDto addItem, @AuthenticationPrincipal CustomUserDetails user) {
        itemService.addItem(addItem, user.getEmail());
        return ResponseEntity.ok("상품이 등록되었습니다.");
    }

    @PutMapping("/updateItem")
    public ResponseEntity<?> updateItem(@RequestBody ItemRegisterDto item) {
        itemService.updateItem(item);
        return ResponseEntity.ok("상품이 수정되었습니다!");
    }

    @DeleteMapping("/deleteItem/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Integer itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok("상품이 삭제되었습니다!");
    }

    @GetMapping("/sales")
    public List<Sale> findAllSaleItem(@AuthenticationPrincipal CustomUserDetails user) {
        return saleService.findSaleItem(user.getUser_id());
    }

}
