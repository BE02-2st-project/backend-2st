package com.github.super_mall.dto.orderDto;

import com.github.super_mall.entity.orderItemEntity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {

    private String imgUrl;
    private String itemName;
    private Integer count;
    private Integer price;
    private Integer totalPrice;

    public OrderItemResponseDto(String imgUrl, OrderItem orderItem){
        this.imgUrl = imgUrl;
        this.itemName = orderItem.getItem().getName();
        this.count = orderItem.getCount();
        this.price = orderItem.getPrice();
        this.totalPrice = count * price;
    }
}
