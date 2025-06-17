package com.aion.orderservice.service;

import com.aion.orderservice.dto.OrderItemDto;
import com.aion.orderservice.entity.OrderItem;
import com.aion.orderservice.entity.Order;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> saveOrderItems(List<OrderItemDto> orderItemsDto, Order order);
}
