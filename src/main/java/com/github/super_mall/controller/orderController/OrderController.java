package com.github.super_mall.controller.orderController;

import com.github.super_mall.dto.orderDto.OrderRequestDto;
import com.github.super_mall.dto.orderDto.OrderResponseDto;
import com.github.super_mall.entity.orderEntity.Order;
import com.github.super_mall.entity.userDetailEntity.CustomUserDetails;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.repository.orderRepository.OrderRepository;
import com.github.super_mall.service.orderService.OrderService;
import com.github.super_mall.util.JwtTokenUtil;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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
public class OrderController {

    private final OrderService orderService;
    private final JwtTokenUtil jwtTokenUtil;
    private final OrderRepository orderRepository;

    @PostMapping("/{itemId}/order")
    public ResponseEntity<?> createOrder(
                @PathVariable Integer itemId,
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
            orderId = orderService.createOrder(itemId, orderDto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

//        return new ResponseEntity<>(orderId, HttpStatus.OK);
        return ResponseEntity.ok("주문이 완료되었습니다.");
    }

    @GetMapping("/orders")
    public List<OrderResponseDto> findAllOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String email = customUserDetails.getEmail();
        return orderService.findAllOrder(email);
    }

}
