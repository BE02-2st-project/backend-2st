package com.github.super_mall.dto.cartDto;

import com.github.super_mall.entity.cartItemEntity.CartItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartListResponseDto {
    List<CartResponseDto> cartResponseDtoList;
    Integer totalPrice;
}
