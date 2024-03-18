package com.github.super_mall.dto.itemDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ItemRegisterDto {

    private Integer userId;
    private Integer itemId;
    private String category;
    private String name;
    private Integer price;
    private Integer stock;
    private List<String> imgURLs = new ArrayList<>();
    private String description;
}
