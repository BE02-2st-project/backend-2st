package com.github.super_mall.entity.myPageEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.super_mall.entity.orderEntity.Order;
import com.github.super_mall.entity.saleEntity.Sale;
import com.github.super_mall.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "my_page")
public class MyPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_page_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_id")
    private Order order;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "basket_id")
//    private Basket basket;

    @Column(name = "profile_img_url")
    private String profileImgURL;

    @Column(name = "address")
    private String address;

}
