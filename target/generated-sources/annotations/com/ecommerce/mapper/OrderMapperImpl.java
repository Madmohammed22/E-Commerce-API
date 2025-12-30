package com.ecommerce.mapper;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.OrderItemDto;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-30T22:39:44+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto.OrderDtoBuilder orderDto = OrderDto.builder();

        orderDto.userId( orderUserId( order ) );
        orderDto.items( toOrderItemDtoSet( order.getOrderItems() ) );
        orderDto.id( order.getId() );
        orderDto.totalAmount( order.getTotalAmount() );
        if ( order.getStatus() != null ) {
            orderDto.status( order.getStatus().name() );
        }
        orderDto.shippingAddress( order.getShippingAddress() );

        return orderDto.build();
    }

    @Override
    public Order toEntity(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.user( orderDtoToUser( orderDto ) );
        order.id( orderDto.getId() );
        order.totalAmount( orderDto.getTotalAmount() );
        if ( orderDto.getStatus() != null ) {
            order.status( Enum.valueOf( Order.OrderStatus.class, orderDto.getStatus() ) );
        }
        order.shippingAddress( orderDto.getShippingAddress() );

        return order.build();
    }

    @Override
    public OrderItemDto toOrderItemDto(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemDto.OrderItemDtoBuilder orderItemDto = OrderItemDto.builder();

        orderItemDto.productId( orderItemProductId( orderItem ) );
        orderItemDto.productName( orderItemProductName( orderItem ) );
        orderItemDto.quantity( orderItem.getQuantity() );
        orderItemDto.price( orderItem.getPrice() );

        return orderItemDto.build();
    }

    @Override
    public Set<OrderItemDto> toOrderItemDtoSet(Set<OrderItem> orderItems) {
        if ( orderItems == null ) {
            return null;
        }

        Set<OrderItemDto> set = LinkedHashSet.newLinkedHashSet( orderItems.size() );
        for ( OrderItem orderItem : orderItems ) {
            set.add( toOrderItemDto( orderItem ) );
        }

        return set;
    }

    private Long orderUserId(Order order) {
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    protected User orderDtoToUser(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( orderDto.getUserId() );

        return user.build();
    }

    private Long orderItemProductId(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }

    private String orderItemProductName(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getName();
    }
}
