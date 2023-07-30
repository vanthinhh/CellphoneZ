package com.bienvan.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bienvan.store.model.OrderItem;
import com.bienvan.store.repository.OrderItemRepository;

@Service
public class OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;
    
    public OrderItem createOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }
}
