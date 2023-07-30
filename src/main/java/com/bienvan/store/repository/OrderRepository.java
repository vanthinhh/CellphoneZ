package com.bienvan.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bienvan.store.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
