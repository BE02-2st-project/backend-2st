package com.github.super_mall.dto.itemDto;

import com.github.super_mall.entity.categoryEntity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemBody {


    private String userId;
    private Category category;
    private String imgURL;
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
}
