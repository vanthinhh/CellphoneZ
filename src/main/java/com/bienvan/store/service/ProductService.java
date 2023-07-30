package com.bienvan.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bienvan.store.model.Product;
import com.bienvan.store.model.dto.ProductDto;
import com.bienvan.store.model.mapper.ProductMapper;
import com.bienvan.store.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<ProductDto>();
        if(!products.isEmpty()){
            for(Product p: products){
                ProductDto tmp = ProductMapper.toProductDto(p);
                productDtos.add(tmp);
            }
            return productDtos;
        }
        return null;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findByName(String name){
        return productRepository.findByName(name);
    }
}
