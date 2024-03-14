package com.github.super_mall.repository.orderRepository;

import com.github.super_mall.entity.orderEntity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(
            "select o from Order o " +
                    "where o.user.email = :email " +
                    "order by o.createAt desc"
    )
    List<Order> findOrders(@Param("email") String email);
}
