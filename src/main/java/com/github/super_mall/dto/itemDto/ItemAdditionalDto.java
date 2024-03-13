package com.github.super_mall.dto.itemDto;

import com.github.super_mall.entity.categoryEntity.Category;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ItemAdditionalDto {

    private String userId;
    private Category category;
    private String imgURL;
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
}
