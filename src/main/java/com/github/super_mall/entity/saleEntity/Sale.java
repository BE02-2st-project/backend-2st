package com.github.super_mall.entity.saleEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "stock")
    private Integer stock;

    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
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
