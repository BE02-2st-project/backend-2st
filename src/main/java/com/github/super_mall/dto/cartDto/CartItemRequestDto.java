package com.github.super_mall.dto.cartDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDto {
    @NotNull(message = "상품아이디는 반드시 입력해야 합니다.")
    private Integer itemId;

    @Min(value = 1, message = "최소 1개 이상 주문해야 합니다.")
    private Integer count;
}
