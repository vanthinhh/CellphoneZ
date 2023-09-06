package com.bienvan.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bienvan.store.model.Order;
import com.bienvan.store.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(User userId);

    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Order> findOrdersByUserId(Long userId);

}
