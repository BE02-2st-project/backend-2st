package com.github.super_mall.entity.itemEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SortComparator;

@Entity
@Table(name = "item_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImage implements Comparable<ItemImage> {
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
    @Setter
    private String repImgURL;

    public static ItemImage toEntity(String image, Item item) {
        return ItemImage.builder()
                .item(item)
                .imageURL(image)
                .build();
    }

    @Override
    public int compareTo(ItemImage itemImage) {
        if (this.id < itemImage.id) {
            return -1;
        }
        // 현재 객체의 값이 다른 객체와 같으면 0 반환
        else if (this.id.equals(itemImage.id)) {
            return 0;
        }
        // 현재 객체의 값이 다른 객체보다 크면 양수 반환
        else {
            return 1;
        }
    }
}
