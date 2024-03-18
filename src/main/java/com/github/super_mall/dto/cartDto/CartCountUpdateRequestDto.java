package com.github.super_mall.dto.cartDto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartCountUpdateRequestDto {
    private Integer count;
}
