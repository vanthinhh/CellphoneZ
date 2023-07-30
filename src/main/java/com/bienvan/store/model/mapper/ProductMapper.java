package com.bienvan.store.model.mapper;

import com.bienvan.store.model.*;
import com.bienvan.store.model.dto.*;

public class ProductMapper {
    public static ProductDto toProductDto(Product p){
        return new ProductDto(p.getId(),p.getName(),p.getDescription(),p.getPrice(),p.getImage(),p.getQuantity());
    }
}
