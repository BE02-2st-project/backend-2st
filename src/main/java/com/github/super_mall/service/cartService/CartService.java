package com.github.super_mall.service.cartService;

import com.github.super_mall.dto.cartDto.CartItemRequestDto;
import com.github.super_mall.dto.cartDto.CartResponseDto;
import com.github.super_mall.entity.cartEntity.Cart;
import com.github.super_mall.entity.cartItemEntity.CartItem;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.exceptions.LoginException;
import com.github.super_mall.repository.cartItemRepository.CartItemRepository;
import com.github.super_mall.repository.cartRepository.CartRepository;
import com.github.super_mall.repository.itemRepository.ItemRepository;
import com.github.super_mall.repository.userRepository.UserRepository;
import com.github.super_mall.service.orderService.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public Long addCart(Integer itemId, CartItemRequestDto cartItemRequestDto, String email){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new LoginException("회원을 찾을 수 없습니다")
        );
        Cart cart = cartRepository.findByUser_UserId(user.getUserId());

        // 카트가 없다면 카드 생성
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        CartItem savedCartItem = cartItemRepository.findByCart_CartIdAndItem_Id(cart.getCartId(), item.getId());

        if (savedCartItem != null) {
            Integer prev = savedCartItem.getCount();
            savedCartItem.addCount(cartItemRequestDto.getCount());
            return savedCartItem.getCartItemId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemRequestDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getCartItemId();
        }
    }

    public List<CartResponseDto> findCartList(String email){
        List<CartResponseDto> cartResponseDtoList = new ArrayList<>();

        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Cart cart = cartRepository.findByUser_UserId(user.getUserId());

        if(cart == null){
            return cartResponseDtoList;
        }

        cartResponseDtoList = cartItemRepository.findCartResponseDtoList(cart.getCartId());
        return cartResponseDtoList;
    }

    public void deleteCartItem(String email, Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }
}
