package com.github.super_mall.controller.cartController;

import com.github.super_mall.dto.cartDto.CartItemRequestDto;
import com.github.super_mall.dto.cartDto.CartListResponseDto;
import com.github.super_mall.dto.cartDto.CartResponseDto;
import com.github.super_mall.dto.cartOrderDto.CartOrderDto;
import com.github.super_mall.entity.cartEntity.Cart;

import com.github.super_mall.entity.userDetailEntity.CustomUserDetails;
import com.github.super_mall.repository.cartRepository.CartRepository;
import com.github.super_mall.service.cartService.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;
    private final CartRepository cartRepository;

    // 장바구니에 담기
    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(
            @Valid @RequestBody CartItemRequestDto cartItemRequestDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            BindingResult bindingResult){

        // binding 에러 시
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = customUserDetails.getEmail();


        try {
            cartService.addCart(cartItemRequestDto, email);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("해당 상품을 장바구니에 추가하였습니다.");
    }

    // 장바구니 조회
    @GetMapping("/cart-list")
    public CartListResponseDto myCartList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Cart cart = cartRepository.findByUser_UserId(customUserDetails.getUser_id());
        String email = customUserDetails.getEmail();

        List<CartResponseDto> cartResponseDtoList = cartService.findCartList(email);

        int totalPrice = 0;
        for (CartResponseDto cartResponseDto : cartResponseDtoList){
            totalPrice += (cartResponseDto.getTotalPrice());
        }

        return CartListResponseDto.builder()
                .cartResponseDtoList(cartResponseDtoList)
                .totalPrice(totalPrice)
                .build();
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/cart-list/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String email = customUserDetails.getEmail();
        cartService.deleteCartItem(email, cartItemId);

        return ResponseEntity.ok("해당 상품이 장바구니에서 삭제되었습니다.");
    }

    // 장바구니에서 주문하기
    @PostMapping("/cart/orders")
    public ResponseEntity<?> orderCartItems(@RequestBody CartOrderDto cartOrderDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if (cartOrderDtoList == null || cartOrderDtoList.isEmpty()){
            return new ResponseEntity<>("주문할 상품을 선택하세요!", HttpStatus.FORBIDDEN);
        }

        String email = customUserDetails.getEmail();

        cartService.orderCartItems(cartOrderDtoList, email);

        return ResponseEntity.ok("주문이 완료되었습니다.");
    }
}
