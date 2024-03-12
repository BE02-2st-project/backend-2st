package com.github.super_mall.repository.orderRepository;

import com.github.super_mall.entity.orderEntity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
