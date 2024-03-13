package com.github.super_mall.dto.categoryDto;

import com.github.super_mall.entity.categoryEntity.Category;
import com.github.super_mall.entity.itemEntity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class CategoryResponseDto {
    private String categoryName;

    public CategoryResponseDto(Category category) {
        this.categoryName = category.getCategory();
    }

    public static CategoryResponseDto of(Item item) {
        return new CategoryResponseDto(item.getCategory());
    }
}
