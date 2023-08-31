package com.bienvan.store.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bienvan.store.model.*;
import com.bienvan.store.service.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    @GetMapping
    public ResponseEntity<?> getListCategory() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        Optional<Category> optional = categoryService.getCategoryById(id);

        if(optional.isPresent()){
            Category data = optional.get();
            res.put("code", 0);
            res.put("message", "Find category successfully");
            res.put("data", data);
        }
        else{
            res.put("code", 1);
            res.put("message", "Find category failed");
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        Map<String, Object> res = new HashMap<>();
        Optional<Category> optional = categoryService.findByName(category.getName());
        if (!optional.isPresent()) {
            Category data = categoryService.createCategory(category);
            res.put("code", 0);
            res.put("message", "Add category successfully");
            res.put("data", data);
        } else {
            res.put("code", 1);
            res.put("message", "Add category failed");
        }
        return ResponseEntity.ok(res);
    }

    @PutMapping // update
    public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody Category category) {
        Map<String, Object> res = new HashMap<>();

        Optional<Category> optional = categoryService.getCategoryById(category.getId());
    
        if (optional.isPresent()) {
            Category existingCategory = optional.get();
            existingCategory.setName(category.getName());
    
            categoryService.createCategory(existingCategory);
            res.put("code", 0);
            res.put("message", "Update category successfully");
        } else {
            res.put("code", 1);
            res.put("message", "Update category failed");
        }
        return ResponseEntity.ok(res);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        Optional<Category> optional = categoryService.getCategoryById(id);

        if (optional.isPresent()) {
            categoryService.deleteCategory(id);
            res.put("code", 0);
            res.put("message", "Delete category successfully");
        } else {
            res.put("code", 1);
            res.put("message", "Delete category failed");     
        }
        return ResponseEntity.ok(res);
    }
}
