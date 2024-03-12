package com.github.super_mall.entity.itemEntity;

import com.github.super_mall.dto.itemDto.ItemAdditionalDto;
import com.github.super_mall.entity.categoryEntity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "items")
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "item_name", nullable = false)
    private String name;

    @Column(name = "img")
    private String imgURL;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "item_description", nullable = false)
    private String description;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    public static Item toEntity(ItemAdditionalDto addItem, Category category) {
        return Item.builder()
                .category(category)
                .name(addItem.getName())
                .price(addItem.getPrice())
                .imgURL(addItem.getImgURL())
                .description(addItem.getDescription())
                .createAt(LocalDateTime.parse(addItem.getCreateAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
