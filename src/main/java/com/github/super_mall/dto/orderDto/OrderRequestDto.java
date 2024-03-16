package com.github.super_mall.dto.orderDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "상품 아이디는 반드시 입력해야 합니다.")
    private Integer itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    private Integer count;
}
