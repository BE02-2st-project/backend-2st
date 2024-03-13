package com.github.super_mall.repository.orderRepository;

import com.github.super_mall.entity.orderEntity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    Order findByUserId(Long userId);
}
