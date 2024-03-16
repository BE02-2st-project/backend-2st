package com.github.super_mall.entity.cartItemEntity;

import com.github.super_mall.entity.cartEntity.Cart;
import com.github.super_mall.entity.itemEntity.Item;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "price")
    private Integer price;

    @Column(name = "count")
    private Integer count;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime createAt;

    // 장바구니에 상품 등록 메소드
    public static CartItem createCartItem(Cart cart, Item item, Integer price, Integer count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setPrice(price);
        cartItem.setCount(count);
        cartItem.setCreateAt(LocalDateTime.now());

        return cartItem;
    }

    // 장바구니에 담겨있는 상품에 추가로 수량을 더할 때 쓰는 메소드
    public void addCount(Integer count){
        this.count += count;
    }
}
