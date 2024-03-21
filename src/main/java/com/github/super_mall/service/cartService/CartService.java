package com.github.super_mall.service.cartService;

import com.github.super_mall.dto.cartDto.CartItemRequestDto;
import com.github.super_mall.dto.cartDto.CartListResponseDto;
import com.github.super_mall.dto.cartDto.CartResponseDto;
import com.github.super_mall.dto.cartOrderDto.CartOrderDto;
import com.github.super_mall.dto.orderDto.OrderRequestDto;
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
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    // 장바구니 생성 & 장바구니에 상품 추가
    @Transactional
    public void addCart(CartItemRequestDto cartItemRequestDto, String email){
        Item item = itemRepository.findById(cartItemRequestDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new LoginException("회원을 찾을 수 없습니다")
        );
        Cart cart = cartRepository.findByUser_UserId(user.getUserId());

        // 장바구니가 없다면 생성
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        // 장바구니에 상품 추가하기
        CartItem cartItem = cartItemRepository.findByCart_CartIdAndItem_Id(cart.getCartId(), item.getId());
        Integer price = item.getPrice();
        Integer count = cartItemRequestDto.getCount();

        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, item, price, count);
            cartItemRepository.save(cartItem);
        } else {
            cartItem.addCount(count);
        }
    }

    // 장바구니 조회
    @Transactional
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

    // 장바구니에서 상품 수량 업데이트
    @Transactional
    public void updateCartItemCount(Long cartItemId, Integer count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    // 장바구니에서 상품 삭제
    @Transactional
    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    // 장바구니에서 주문하기
    @Transactional
    public void orderCartItems(List<CartOrderDto> cartOrderDtoList, String email) {
        List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();

        // 주문할 상품 List에 담기
        for (CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderRequestDto orderRequestDto = new OrderRequestDto();
            orderRequestDto.setItemId(cartItem.getItem().getId());
            orderRequestDto.setCount(cartItem.getCount());
            orderRequestDtoList.add(orderRequestDto);
        }

        // 주문했다면 장바구니에서 상품 제거
        orderService.createOrders(orderRequestDtoList, email);
        for (CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            cartItemRepository.delete(cartItem);
        }
    }

    // 회원검증
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        User cartSaveUser = cartItem.getCart().getUser();

        if (!user.equals(cartSaveUser)){
            return false;
        }

        return true;
    }
}
