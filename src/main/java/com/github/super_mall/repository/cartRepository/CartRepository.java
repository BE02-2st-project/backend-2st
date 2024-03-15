package com.github.super_mall.repository.cartRepository;

import com.github.super_mall.entity.cartEntity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_UserId(Long userId);
}
