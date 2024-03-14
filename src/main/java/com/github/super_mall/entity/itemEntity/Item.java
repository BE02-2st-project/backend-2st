package com.github.super_mall.entity.itemEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.super_mall.dto.itemDto.ItemAdditionalDto;
import com.github.super_mall.entity.categoryEntity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "items")
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "item_name")
    private String name;

    @Column(name = "img")
    private String imgURL;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "item_description")
    private String description;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    public static Item toEntity(ItemAdditionalDto addItem, Category category) {
        return Item.builder()
                .category(category)
                .name(addItem.getName())
                .price(addItem.getPrice())
                .stock(addItem.getStock())
                .imgURL(addItem.getImgURL())
                .description(addItem.getDescription())
                .createAt(LocalDateTime.now())
                .build();
    }

    public void removeStock(Integer count) {
        stock = stock - count;
    }

    public void addStock(Integer count) {
        stock = stock + count;
    }
}
