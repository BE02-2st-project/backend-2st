package com.github.super_mall.service.orderService;

import com.github.super_mall.dto.orderDto.OrderItemResponseDto;
import com.github.super_mall.dto.orderDto.OrderRequestDto;
import com.github.super_mall.dto.orderDto.OrderResponseDto;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.orderEntity.Order;
import com.github.super_mall.entity.orderItemEntity.OrderItem;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.exceptions.LoginException;
import com.github.super_mall.repository.itemRepository.ItemRepository;
import com.github.super_mall.repository.orderRepository.OrderRepository;
import com.github.super_mall.repository.userRepository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    // 주문 생성
    public Long createOrder(Integer itemId , OrderRequestDto orderDto, String email) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new LoginException("회원을 찾을 수 없습니다")
        );

        Integer price = item.getPrice();

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, price, orderDto.getCount());
        orderItemList.add(orderItem);
        Order order = Order.createOrder(user, orderItemList);
        order.setTotalPrice(order.getTotalPrice());
        orderRepository.save(order);

        return order.getOderId();
    }

    // 주문 조회
    public List<OrderResponseDto> findAllOrder(String email) {
        List<Order> orderList = orderRepository.findOrders(email);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for(Order order : orderList){
            OrderResponseDto orderResponseDto = new OrderResponseDto(order);
            List<OrderItem> orderItemList = order.getOrderItemList();
            for(OrderItem orderItem : orderItemList){
                OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto(orderItem);
                orderResponseDto.addOrderItemDto(orderItemResponseDto);
            }

            orderResponseDtoList.add(orderResponseDto);
        }

       return orderResponseDtoList;
    }

    // 주문 취소
    public void deleteOrder(Long orderId, String email) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.deleteOrder();
    }
}
