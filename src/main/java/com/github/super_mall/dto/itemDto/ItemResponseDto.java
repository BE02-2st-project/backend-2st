package com.github.super_mall.dto.itemDto;


import com.github.super_mall.entity.itemEntity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemResponseDto {
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
    private LocalDateTime createAt;

    public ItemResponseDto(Item item) {

    }
}
