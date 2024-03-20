package com.github.super_mall.dto.cartDto;

import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.itemEntity.ItemImage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponseDto {
    private Long cartItemId;
    private String imgUrl;
    private String itemName;
    private Integer price;
    private Integer count;
    private Integer totalPrice;

    public CartResponseDto(Long cartItemId, String imgUrl, String itemName, Integer price, Integer count) {
        this.cartItemId = cartItemId;
        this.imgUrl = imgUrl;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.totalPrice = price * count;
    }
}
