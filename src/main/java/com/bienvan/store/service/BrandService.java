package com.bienvan.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bienvan.store.model.Brand;
import com.bienvan.store.repository.BrandRepository;

@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;

    public List<Brand> getBrands(){
        return brandRepository.findAll();
    }

    public Brand findByName(String name){
        return brandRepository.findByName(name).get();
    }

    public Brand findById(Long id){
        if(id == null){
            return null;
        }
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            return brandOptional.get();
        }
        return null;
    }
    
}
