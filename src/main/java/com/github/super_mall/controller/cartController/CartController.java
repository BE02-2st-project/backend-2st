package com.github.super_mall.controller.cartController;

import com.github.super_mall.dto.cartDto.CartItemRequestDto;
import com.github.super_mall.dto.cartDto.CartResponseDto;
import com.github.super_mall.entity.userDetailEntity.CustomUserDetails;
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

    // 장바구니에 담기
    @PostMapping("/{itemId}/cart")
    public ResponseEntity<?> addToCart(
            @PathVariable Integer itemId,
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

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = customUserDetails.getEmail();
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(itemId, cartItemRequestDto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    // 장바구니 조회
    @GetMapping("/cart-list")
    public List<CartResponseDto> cartResponseDtoList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String email = customUserDetails.getEmail();
        return cartService.findCartList(email);
    }

    // 장바구니에서 아이템 삭제
    @DeleteMapping("/cart-list/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String email = customUserDetails.getEmail();
        cartService.deleteCartItem(email, cartItemId);

        return ResponseEntity.ok("해당 상품이 장바구니에서 삭제되었습니다.");
    }
}
