package com.ecommerce.mapper;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "items")
    OrderDto toDto(Order order);

    @Mapping(source = "userId", target = "user.id")
    Order toEntity(OrderDto orderDto);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    Set<OrderItemDto> toOrderItemDtoSet(Set<OrderItem> orderItems);
}