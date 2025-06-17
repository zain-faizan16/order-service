package com.aion.orderservice.controller;

import com.aion.orderservice.dto.ApiResponse;
import com.aion.orderservice.dto.OrderDto;
import com.aion.orderservice.service.OrderService;
import com.aion.orderservice.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(@RequestBody @Valid OrderDto orderDto) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(new ApiResponse<>(Constants.STATUS_CODE_CREATED, Constants.STATUS_SUCCESS_MESSAGE, createdOrder), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrdersByEmail(@PathVariable String email) {
        List<OrderDto> orders = orderService.getOrdersByUserEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(Constants.STATUS_CODE_SUCCESS, Constants.STATUS_SUCCESS_MESSAGE, orders));
    }
}

