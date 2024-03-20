package com.github.super_mall.repository.cartItemRepository;

import com.github.super_mall.dto.cartDto.CartResponseDto;
import com.github.super_mall.entity.cartItemEntity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCart_CartIdAndItem_Id(Long cartId, Integer itemId);

//    @Query("select new com.github.super_mall.dto.cartDto.CartResponseDto(ci.cartItemId, i.name, i.price, ci.count) " +
//            "from CartItem ci " +
//            "join ci.item i " +
//            "where ci.cart.cartId = :cartId " +
//            "order by ci.createAt desc"
//    )
//    List<CartResponseDto> findCartResponseDtoList(Long cartId);

    @Query("select new com.github.super_mall.dto.cartDto.CartResponseDto(ci.cartItemId, im.imageURL, i.name, i.price, ci.count) " +
            "from CartItem ci, ItemImage im " +
            "join ci.item i " +
            "where ci.cart.cartId = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repImgURL = 'Y' " +
            "order by ci.createAt desc"
    )
    List<CartResponseDto> findCartResponseDtoList(Long cartId);
}
