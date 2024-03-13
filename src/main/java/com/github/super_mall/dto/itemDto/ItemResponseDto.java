package com.github.super_mall.dto.itemDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemResponseDto {
    private List<ItemRegisterDto> itemRegisterDtoList;
}
