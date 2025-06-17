package com.aion.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    private UUID id;

    private String userEmail;

    private BigDecimal totalAmount;

    private LocalDateTime createdAt;
}

