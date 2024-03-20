package com.github.super_mall.entity.orderEntity;

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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_Id")
    private Long oderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 주문할 아이템의 리스트를 들고온다.
    @Builder.Default
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    // 총 금액
    @Column(name = "total_price")
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @Column(name = "create_at")
    private LocalDateTime createAt;

    // OrderItem 생성 메소드
    public void addOrderItem(OrderItem orderItem){
        orderItemList.add(orderItem);
        orderItem.setOrders(this);
    }

    // 생성 메소드
    public static Orders createOrder(User user, List<OrderItem> orderItemList){
        Orders orders = new Orders();
        orders.setUser(user);

        for (OrderItem orderItem : orderItemList){
            orders.addOrderItem(orderItem);
        }
        orders.setStatus(OrderStatus.ORDER);
        orders.setCreateAt(LocalDateTime.now());

        return orders;
    }

    // 총 가격
    public Integer getTotalPrice(){
        int orderTotalPrice = 0;
        for (OrderItem orderItem : orderItemList){
            orderTotalPrice += (orderItem.getPrice() * orderItem.getCount());
        }
        return orderTotalPrice;
    }

    // 주문 취소 메소드
    public void cancelOrder() {
        this.status = OrderStatus.CANCEL;
        for(OrderItem orderItem : orderItemList){
            orderItem.cancel();
        }
    }
}
