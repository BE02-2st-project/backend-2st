package com.github.super_mall.controller.orderController;

import com.github.super_mall.dto.orderDto.OrderRequestDto;
import com.github.super_mall.dto.orderDto.OrderResponseDto;
import com.github.super_mall.entity.userDetailEntity.CustomUserDetails;
import com.github.super_mall.repository.orderRepository.OrderRepository;
import com.github.super_mall.service.orderService.OrderService;
import com.github.super_mall.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final JwtTokenUtil jwtTokenUtil;
    private final OrderRepository orderRepository;

    // 상품 주문
    @PostMapping("/order")
    public ResponseEntity<?> createOrder(
                @Valid @RequestBody OrderRequestDto orderDto,
                @AuthenticationPrincipal CustomUserDetails customUserDetails,
                BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = customUserDetails.getEmail();
        Long orderId;

        try {
            orderService.createOrder(orderDto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("주문이 완료되었습니다.", HttpStatus.OK);
    }

    // 주문 조회
    @Transactional
    @GetMapping("/order-list")
    public List<OrderResponseDto> findAllOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String email = customUserDetails.getEmail();
        return orderService.findAllOrder(email);
    }


    // 주문 취소
    @Transactional
    @PostMapping("/order-list/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String email = customUserDetails.getEmail();

        if (!orderService.validateOrder(orderId, email)){
            return new ResponseEntity<>("주문 취소 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);

        return new ResponseEntity<String>("주문이 취소되었습니다.", HttpStatus.OK);
    }
}
