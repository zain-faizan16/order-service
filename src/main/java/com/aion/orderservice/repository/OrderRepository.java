package com.aion.orderservice.repository;

import com.aion.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserEmail(String userEmail);
}

