package com.aion.orderservice;

import com.aion.orderservice.dto.OrderDto;
import com.aion.orderservice.dto.OrderItemDto;
import com.aion.orderservice.entity.Order;
import com.aion.orderservice.entity.OrderItem;
import com.aion.orderservice.repository.OrderRepository;
import com.aion.orderservice.service.OrderItemService;
import com.aion.orderservice.service.PaymentService;
import com.aion.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private RabbitTemplate rabbitTemplate;
    private OrderItemService orderItemService;
    private PaymentService paymentService;
    private ModelMapper modelMapper;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        rabbitTemplate = mock(RabbitTemplate.class);
        orderItemService = mock(OrderItemService.class);
        paymentService = mock(PaymentService.class);
        modelMapper = new ModelMapper();

        orderService = new OrderServiceImpl(orderRepository, modelMapper, rabbitTemplate, orderItemService, paymentService);

        // Set RabbitMQ properties (since they are injected via @Value in original service)
        // You can use reflection if needed, but it's better to add a constructor for testability in real code
        try {
            var exchangeField = OrderServiceImpl.class.getDeclaredField("exchange");
            exchangeField.setAccessible(true);
            exchangeField.set(orderService, "test.exchange");

            var routingKeyField = OrderServiceImpl.class.getDeclaredField("routingKey");
            routingKeyField.setAccessible(true);
            routingKeyField.set(orderService, "test.key");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateOrder_success() {
        // Given
        OrderDto dto = new OrderDto();
        dto.setUserEmail("user@example.com");
        dto.setTotalAmount(BigDecimal.valueOf(100));
        dto.setOrderItemDtoList(List.of(new OrderItemDto()));

        Order order = modelMapper.map(dto, Order.class);
        order.setId(UUID.randomUUID());
        order.setCreatedAt(LocalDateTime.now());

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderItemService.saveOrderItems(anyList(), any(Order.class))).thenReturn(List.of(new OrderItem()));

        // When
        OrderDto result = orderService.createOrder(dto);

        // Then
        assertNotNull(result);
        assertEquals("user@example.com", result.getUserEmail());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(paymentService, times(1)).initiatePayment(any(UUID.class), any(BigDecimal.class));
    }

    @Test
    void testGetOrdersByUserEmail_success() {
        // Given
        String email = "user@example.com";
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserEmail(email);
        order.setTotalAmount(BigDecimal.valueOf(100));
        order.setCreatedAt(LocalDateTime.now());

        when(orderRepository.findByUserEmail(email)).thenReturn(List.of(order));

        // When
        List<OrderDto> result = orderService.getOrdersByUserEmail(email);

        // Then
        assertEquals(1, result.size());
        assertEquals(email, result.get(0).getUserEmail());
        verify(orderRepository, times(1)).findByUserEmail(email);
    }
}
