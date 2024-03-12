package com.github.super_mall.dto.orderDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    private Integer count;
}
