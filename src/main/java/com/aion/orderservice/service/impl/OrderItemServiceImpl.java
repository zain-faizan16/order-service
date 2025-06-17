package com.aion.orderservice.service.impl;

import com.aion.orderservice.dto.OrderItemDto;
import com.aion.orderservice.entity.Order;
import com.aion.orderservice.entity.OrderItem;
import com.aion.orderservice.repository.OrderItemRepository;
import com.aion.orderservice.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderItem> saveOrderItems(List<OrderItemDto> orderItemsDto, Order order) {
        List<OrderItem>  orderItems = orderItemsDto.stream().map(a ->
                modelMapper.map(a,OrderItem.class)).collect(Collectors.toList());
        for (OrderItem item : orderItems) {
            item.setOrderItemId(UUID.randomUUID());
            item.setOrder(order);

        }
        return orderItemRepository.saveAll(orderItems);

    }
}
