package com.aion.orderservice.service.impl;

import com.aion.orderservice.dto.PaymentRequest;
import com.aion.orderservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;

    @Value("${payment.service.url}") // e.g. http://localhost:8082/payment
    private String paymentServiceUrl;

    public void initiatePayment(UUID orderId, BigDecimal amount) {
        PaymentRequest paymentRequest = new PaymentRequest(orderId, amount);

        ResponseEntity<String> response = restTemplate.postForEntity(
                paymentServiceUrl, paymentRequest, String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Payment initiated successfully: {}", response.getBody());
        } else {
            log.error("Failed to initiate payment: {}", response.getStatusCode());
            throw new RuntimeException("Payment failed to initiate");
        }
    }

}
