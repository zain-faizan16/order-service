package com.aion.orderservice.dto;

import com.aion.orderservice.entity.Order;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemDto {
    private UUID orderItemId;
    private UUID productId;
    private int quantity;
    private Order order;
}
