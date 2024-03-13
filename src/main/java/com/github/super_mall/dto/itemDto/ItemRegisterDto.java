package com.github.super_mall.dto.itemDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ItemRegisterDto {
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
    private LocalDateTime createAt;

}
