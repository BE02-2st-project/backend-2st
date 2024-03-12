package com.github.super_mall.service.orderService;

import com.github.super_mall.dto.orderDto.OrderDto;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.orderEntity.Order;
import com.github.super_mall.entity.orderItemEntity.OrderItem;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.repository.itemRepository.ItemRepository;
import com.github.super_mall.repository.orderRepository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

//    private final ItemRepository itemRepository;
//    private final UserRepository userRepository;
//    private final OrderRepository orderRepository;
//
//
//    // 주문 생성
//    public Long createOrder(OrderDto orderDto, String email) {
//        Item item = itemRepository.findById(orderDto.getItemId())
//                .orElseThrow(EntityNotFoundException::new);
//
//        User user = userRepository.findByEmail(email);
//
//        List<OrderItem> orderItemList = new ArrayList<>();
//        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
//        orderItemList.add(orderItem);
//        Order order = Order.createOrder(user, orderItemList);
//        orderRepository.save(order);
//
//        return order.getOderId();
//    }
}
