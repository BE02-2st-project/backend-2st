package com.github.super_mall.controller.saleController;

import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.saleEntity.Sale;
import com.github.super_mall.entity.userDetailEntity.CustomUserDetails;
import com.github.super_mall.service.itemService.ItemService;
import com.github.super_mall.service.saleService.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Slf4j
public class SaleController {

    private final ItemService itemService;
    private final SaleService saleService;

    @GetMapping("/sales")
    public List<Sale> findAllSaleItem(@AuthenticationPrincipal CustomUserDetails user) {
        return saleService.findSaleItem(user.getUser_id());
    }
}
