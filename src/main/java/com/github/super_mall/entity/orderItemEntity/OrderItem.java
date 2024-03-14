package com.github.super_mall.entity.orderItemEntity;

import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.orderEntity.Order;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "price")
    private Integer price;

    @Column(name = "count")
    private Integer count;

    // 주문할 아이템 생성 메소드
    public static OrderItem createOrderItem(Item item, Integer price, Integer count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setPrice(price);
        orderItem.setCount(count);
//        item.removeStock(count); // 아이템재고에서 count만큼 감소
        return orderItem;
    }

    // 주문 취소 시 아이템에 stock 추가
//    public void cancel(){
//        getItem().addStock(count);
//    }
}
