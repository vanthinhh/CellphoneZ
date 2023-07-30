package com.bienvan.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bienvan.store.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findByName(String name);
}
