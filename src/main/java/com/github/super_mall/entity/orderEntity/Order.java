package com.github.super_mall.entity.orderEntity;

import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.orderItemEntity.OrderItem;
import com.github.super_mall.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 주문할 아이템의 리스트를 들고온다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    // 총 금액
    private int totalPrice;

    // 만약 배송지를 추가한다면 여기?

    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void addOrderItem(OrderItem orderItem){
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    // 생성 메소드
    public static Order createOrder(User user, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setUser(user);

        for (OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setCreateAt(LocalDateTime.now());

        return order;
    }

    // 총 가격
    public Integer getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList){
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }

//    // 주문 취소
//    public void orderCancel(){
//        this.setStatus(OrderStatus.CANCEL);
//        for(OrderItem orderItem : orderItemList){
//            orderItem.cancel();
//        }
//    }
}
