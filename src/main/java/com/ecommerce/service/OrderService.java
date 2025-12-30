package com.ecommerce.service;
import com.ecommerce.dto.*;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    @Transactional
    public OrderDto createOrder(Long userId, OrderDto orderDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .user(user)
                .totalAmount(0.0)
                .status(Order.OrderStatus.PENDING)
                .shippingAddress(orderDto.getShippingAddress())
                .build();

        double total = 0.0;

        for (OrderItemDto itemDto : orderDto.getItems()) {
            ProductDto product = productService.getProductById(itemDto.getProductId());
            productService.updateStock(product.getId(), itemDto.getQuantity());

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(Product.builder().id(product.getId()).build())
                    .quantity(itemDto.getQuantity())
                    .price(product.getPrice())
                    .build();

            order.getOrderItems().add(item);
            total += product.getPrice() * itemDto.getQuantity();
        }

        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);

        return convertToDto(saved);
    }

    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .toList();
    }

    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDto(order);
    }

    @Transactional
    public OrderDto updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(Order.OrderStatus.valueOf(status));
        return convertToDto(orderRepository.save(order));
    }

    private OrderDto convertToDto(Order order) {
        Set<OrderItemDto> items = order.getOrderItems().stream()
                .map(item -> OrderItemDto.builder()
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(java.util.stream.Collectors.toSet());

        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .shippingAddress(order.getShippingAddress())
                .items(items)
                .build();
    }
}