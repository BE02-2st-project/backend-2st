package com.github.super_mall.dto.orderDto;

import com.github.super_mall.entity.orderEntity.Order;
import com.github.super_mall.entity.orderEntity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemResponseDto> orderItemResponseDtoList = new ArrayList<>();

    // 주문 상품 리스트
    public void addOrderItemDto(OrderItemResponseDto orderItemResponseDto){
        orderItemResponseDtoList.add(orderItemResponseDto);
    }

    public OrderResponseDto(Order order) {
        this.orderId = order.getOderId();
        this.orderDate = order.getCreateAt();
        this.orderStatus = order.getStatus();
    }
}
