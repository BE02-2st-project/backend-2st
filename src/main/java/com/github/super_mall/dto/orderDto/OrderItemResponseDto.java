package com.github.super_mall.dto.orderDto;

import com.github.super_mall.entity.orderItemEntity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {

    private String itemName;
    private Integer count;
    private Integer price;
//    private String imgUrl;

    public OrderItemResponseDto(OrderItem orderItem){
        this.itemName = orderItem.getItem().getName();
        this.count = orderItem.getCount();
        this.price = orderItem.getPrice();
    }
}
