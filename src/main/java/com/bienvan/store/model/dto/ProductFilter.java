package com.bienvan.store.model.dto;

import com.bienvan.store.model.Category;

import lombok.Data;

@Data
public class ProductFilter {
    private double minPrice;
    private double maxPrice;
    private String brand;
    private String color;
    private Category category;
}
