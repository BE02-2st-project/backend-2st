package com.github.super_mall.controller.itemController;

import com.github.super_mall.service.itemService.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

}
