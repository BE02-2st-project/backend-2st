package com.github.super_mall.controller.orderController;

import com.github.super_mall.dto.orderDto.OrderDto;
import com.github.super_mall.service.orderService.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

//    @PostMapping("/order")
//    public @ResponseBody ResponseEntity<?> createOrder(
//            @RequestBody @Valid OrderDto orderDto,
//            BindingResult bindingResult,
//            Principal principal){
//        if(bindingResult.hasErrors()){
//            StringBuilder sb = new StringBuilder();
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//
//            for (FieldError fieldError : fieldErrors) {
//                sb.append(fieldError.getDefaultMessage());
//            }
//
//            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
//        }
//
//        String email = principal.getName();
//        Long orderId;
//
//        try {
//            orderId = orderService.createOrder(orderDto, email);
//        } catch(Exception e){
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
//    }
}
