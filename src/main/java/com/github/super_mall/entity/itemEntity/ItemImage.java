package com.github.super_mall.entity.itemEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    @JsonIgnore
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonIgnore
    private Item item;

    @Column(name = "image_url")
    private String imageURL;

    // 대표이미지 찾기
    @Column(name = "rep_img")
    private String repImgURL;

    public static ItemImage toEntity(String image, Item item) {
        return ItemImage.builder()
                .item(item)
                .imageURL(image)
                .build();
    }
}
