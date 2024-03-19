package com.github.super_mall.entity.itemEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_image")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "image_url")
    private String imageURL;

    public static ItemImage toEntity(String image, Item item) {
        return ItemImage.builder()
                .item(item)
                .imageURL(image)
                .build();
    }
}
