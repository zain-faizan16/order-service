package com.aion.orderservice.service.impl;

import com.aion.orderservice.dto.OrderCreatedEvent;
import com.aion.orderservice.dto.OrderDto;
import com.aion.orderservice.dto.OrderItemDto;
import com.aion.orderservice.entity.Order;
import com.aion.orderservice.entity.OrderItem;
import com.aion.orderservice.repository.OrderRepository;
import com.aion.orderservice.service.OrderItemService;
import com.aion.orderservice.service.OrderService;
import com.aion.orderservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;
    private final OrderItemService orderItemService;
    private final PaymentService paymentService;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Override
    public OrderDto createOrder(OrderDto dto) {
        Order order = modelMapper.map(dto, Order.class);
        order.setId(UUID.randomUUID());
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);
        List<OrderItem> orderItems = orderItemService.saveOrderItems(dto.getOrderItemDtoList(), order);
        paymentService.initiatePayment(order.getId(),order.getTotalAmount());


        rabbitTemplate.convertAndSend(
                exchange, routingKey,
                new OrderCreatedEvent(order.getId(), order.getUserEmail(), order.getTotalAmount(),orderItems.stream().map(a ->
                        modelMapper.map(a,OrderItemDto.class)).collect(Collectors.toList()))
        );

        return modelMapper.map(order, OrderDto.class);
    }


    @Override
    @Cacheable(value = "orders", key = "#email")
    public List<OrderDto> getOrdersByUserEmail(String email) {
        return orderRepository.findByUserEmail(email)
                .stream()
                .map(a ->modelMapper.map(a,OrderDto.class))
                .collect(Collectors.toList());
    }
}

