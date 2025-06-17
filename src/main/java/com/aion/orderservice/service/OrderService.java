package com.aion.orderservice.service;

import com.aion.orderservice.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> getOrdersByUserEmail(String email);
}

