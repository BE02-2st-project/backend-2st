package com.github.super_mall.dto.itemDto;

import com.github.super_mall.entity.categoryEntity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemAdditionalDto {

    private String userId;
    private Integer productId;
    private Category category;
    private String imgURL;
    private String name;
    private Integer price;
    private String description;
    private String createAt;
}
