package com.aion.orderservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String userEmail;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemDto> orderItemDtoList;
}

