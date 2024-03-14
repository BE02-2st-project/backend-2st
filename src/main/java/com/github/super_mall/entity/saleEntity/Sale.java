package com.github.super_mall.entity.saleEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.super_mall.dto.itemDto.ItemAdditionalDto;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "sales")
public class Sale {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    public static Sale toEntity(Item item, User user) {
        return Sale.builder()
                .user(user)
                .item(item)
                .stock(item.getStock())
                .createAt(item.getCreateAt())
                .build();
    }
}
