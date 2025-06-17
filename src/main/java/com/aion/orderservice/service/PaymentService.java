package com.aion.orderservice.service;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {
    void initiatePayment(UUID orderId, BigDecimal amount);
}
