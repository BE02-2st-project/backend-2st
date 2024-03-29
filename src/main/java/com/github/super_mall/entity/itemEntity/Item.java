package com.github.super_mall.entity.itemEntity;

import com.github.super_mall.dto.itemDto.ItemRegisterDto;
import com.github.super_mall.entity.categoryEntity.Category;
import com.github.super_mall.exceptions.OutOfStockException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "items")
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "item_name")
    private String name;

    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private List<ItemImage> imgURLs;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "item_description")
    private String description;

    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "is_delete")
    private boolean isDelete = false;

    public static Item toEntity(ItemRegisterDto addItem, Category category) {
        return Item.builder()
                .category(category)
                .name(addItem.getName())
                .color(addItem.getColor())
                .price(addItem.getPrice())
                .stock(addItem.getStock())
                .description(addItem.getDescription())
                .createAt(LocalDateTime.now())
                .build();
    }

    // 상품 주문 시 재고 감소 메소드
    public void removeStock(Integer count) {
        int remainingStock = this.stock - count;

        if (remainingStock < 0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stock + ")");
        }

        this.stock = remainingStock;
    }

    // 주문 취소 시 상품 재고 증가 메소드
    public void addStock(Integer count) {
        this.stock = stock + count;
    }

    public void deleteItem() {
        isDelete = true;
    }
}
