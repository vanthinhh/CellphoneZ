package com.bienvan.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bienvan.store.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    
}
