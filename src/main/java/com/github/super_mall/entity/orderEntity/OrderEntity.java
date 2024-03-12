package com.github.super_mall.entity.orderEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oderId;

//    @JoinColumn(name = "user_id")
//    @ManyToOne
//    private User user;

//    @JoinColumn(name = "item_id")
//    @ManyToOne
//    private Item item;



    private LocalDateTime createAt;
}
