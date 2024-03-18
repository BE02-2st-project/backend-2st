package com.github.super_mall.service.orderService;

import com.github.super_mall.dto.orderDto.OrderItemResponseDto;
import com.github.super_mall.dto.orderDto.OrderRequestDto;
import com.github.super_mall.dto.orderDto.OrderResponseDto;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.orderEntity.Orders;
import com.github.super_mall.entity.orderItemEntity.OrderItem;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.exceptions.LoginException;
import com.github.super_mall.repository.cartRepository.CartRepository;
import com.github.super_mall.repository.itemRepository.ItemRepository;
import com.github.super_mall.repository.orderRepository.OrderRepository;
import com.github.super_mall.repository.userRepository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    // 주문 생성
    public void createOrder(OrderRequestDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new LoginException("회원을 찾을 수 없습니다")
        );

        List<OrderItem> orderItemList = new ArrayList<>();

        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Orders orders = Orders.createOrder(user, orderItemList);
        orders.setTotalPrice(orders.getTotalPrice());
        orderRepository.save(orders);
    }

    // 장바구니에서 주문할 상품리스트를 받아서 주문 생성
    public void createOrders(List<OrderRequestDto> orderRequestDtoList, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderRequestDto orderRequestDto : orderRequestDtoList){
            Item item = itemRepository.findById(orderRequestDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            Integer price = item.getPrice();

            OrderItem orderItem = OrderItem.createOrderItem(item, orderRequestDto.getCount());
            orderItemList.add(orderItem);
        }

        Orders orders = Orders.createOrder(user, orderItemList);
        orders.setTotalPrice(orders.getTotalPrice());

        orderRepository.save(orders);
    }

    // 주문 조회
    public List<OrderResponseDto> findAllOrder(String email) {
        List<Orders> ordersList = orderRepository.findOrders(email);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for(Orders orders : ordersList){
            OrderResponseDto orderResponseDto = new OrderResponseDto(orders);
            List<OrderItem> orderItemList = orders.getOrderItemList();
            for(OrderItem orderItem : orderItemList){
                OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto(orderItem);
                orderResponseDto.addOrderItemDto(orderItemResponseDto);
            }

            orderResponseDtoList.add(orderResponseDto);
        }

       return orderResponseDtoList;
    }

    // 주문 취소
    public void deleteOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        orders.deleteOrder();
    }

    // DB에 있는 email과 주문자 email 비교
    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);

        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        User orderSaveUser = orders.getUser(); // 주문을 한 유저의 정보를 받기

        if(!user.getEmail().equals(orderSaveUser.getEmail())){
            return false;
        }

        return true;
    }
}
