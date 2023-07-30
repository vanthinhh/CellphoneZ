package com.bienvan.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bienvan.store.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}