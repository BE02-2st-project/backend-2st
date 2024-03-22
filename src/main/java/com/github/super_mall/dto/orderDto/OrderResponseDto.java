package com.github.super_mall.dto.orderDto;

import com.github.super_mall.entity.orderEntity.Orders;
import com.github.super_mall.entity.orderEntity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemResponseDto> orderItemResponseDtoList = new ArrayList<>();
    private Integer orderTotalPrice;

    // 주문 상품 리스트
    public void addOrderItemDto(OrderItemResponseDto orderItemResponseDto){
        orderItemResponseDtoList.add(orderItemResponseDto);
    }

    public OrderResponseDto(Orders orders) {
        this.orderId = orders.getOderId();
        this.orderDate = orders.getCreateAt();
        this.orderStatus = orders.getStatus();
        this.orderTotalPrice = orders.getTotalPrice();
    }
}
