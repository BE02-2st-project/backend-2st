package com.github.super_mall.dto.cartDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDto {
    private Long cartItemId;
    private String itemName;
    private Integer price;
    private Integer count;

    public CartResponseDto(Long cartItemId, String itemName, Integer price, Integer count) {
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
    }
}
