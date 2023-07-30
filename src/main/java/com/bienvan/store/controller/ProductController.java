package com.bienvan.store.controller;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.bienvan.store.model.*;
import com.bienvan.store.model.dto.*;
import com.bienvan.store.service.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getListProduct() {
        List<ProductDto> products = productService.getAllProducts();
        System.out.println("Hello");
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        Optional<Product> optional = productService.getProductById(id);

        if (optional.isPresent()) {
            res.put("code", 0);
            res.put("message", "Find product successfully");
            // res.put("data", data);
        } else {
            res.put("code", 1);
            res.put("message", "Find product failed");
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@ModelAttribute @Valid ProductDto productDto, BindingResult bindingResult)
            throws IOException {
        Map<String, Object> res = new HashMap<>();
        // System.out.println("Post: "+productDto.toString());
        if (bindingResult.hasErrors()) {
            res.put("code", 1);
            res.put("message", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(res);
        }

        String fileType = productDto.getImage().getContentType();
        if (fileType != null && !fileType.startsWith("image/")) {
            res.put("code", 1);
            res.put("message", "Vui lòng chọn tệp tin dạng hình ảnh");
            return ResponseEntity.badRequest().body(res);
        }

        if (fileType == null) {
            res.put("code", 1);
            res.put("message", "Vui lòng thêm hình ảnh");
            return ResponseEntity.badRequest().body(res);
        }

        Optional<User> userOptional = userService.getUserById(productDto.getUser_id());
        if (userOptional.isEmpty()) {
            res.put("code", 1);
            res.put("message", "Vui lòng chọn chính xác người thêm sản phẩm");
            return ResponseEntity.badRequest().body(res);
        }

        User user = userOptional.get();
        Category category = categoryService.getCategoryById(productDto.getCategory_id()).orElse(null);

        if (category == null) {
            res.put("code", 1);
            res.put("message", "Vui lòng chọn loại sản phẩm");
            return ResponseEntity.badRequest().body(res);
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setImage(productDto.getImage().getOriginalFilename());
        product.setUser(user);
        product.setCategory(category);
        Product created = productService.createProduct(product);

        String staticFolderPath = "src/main/resources/static/img/product/" + created.getId() + "/";
        Path folderPath = Paths.get(staticFolderPath);
        try {
            Files.createDirectories(folderPath);
            System.out.println("Folder created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create folder.");
        }

        Path path = Paths.get(staticFolderPath + product.getImage());
        Files.write(path, productDto.getImage().getBytes());

        res.put("code", 0);
        res.put("message", "Thêm sản phẩm thành công");
        return ResponseEntity.ok(res);
    }

    @PutMapping // update
    public ResponseEntity<Map<String, Object>> updateCategory(@ModelAttribute @Valid ProductDto productDto,
            BindingResult bindingResult) throws IOException {
        Map<String, Object> res = new HashMap<>();

        // System.out.println("Put: "+productDto.toString());
        if (bindingResult.hasErrors()) {
            res.put("code", 1);
            res.put("message", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(res);
        }

        String fileType = productDto.getImage().getContentType();
        if (fileType != null && !fileType.startsWith("image/")) {
            res.put("code", 1);
            res.put("message", "Vui lòng chọn tệp tin dạng hình ảnh");
            return ResponseEntity.badRequest().body(res);
        }

        if (fileType == null) {
            res.put("code", 1);
            res.put("message", "Vui lòng thêm hình ảnh");
            return ResponseEntity.badRequest().body(res);
        }

        Optional<Product> optional = productService.getProductById(productDto.getId());

        if (optional.isPresent()) {

            // User user = userService.getUserById(productDto.getUser_id()).orElse(null);
            // if (user == null) {
            //     res.put("code", 1);
            //     res.put("message", "Vui lòng chọn chính xác người thêm sản phẩm");
            //     return ResponseEntity.badRequest().body(res);
            // }

            Category category = categoryService.getCategoryById(productDto.getCategory_id()).orElse(null);
            if (category == null) {
                res.put("code", 1);
                res.put("message", "Vui lòng chọn loại sản phẩm");
                return ResponseEntity.badRequest().body(res);
            }

            Product existing = optional.get();
            existing.setName(productDto.getName());
            existing.setDescription(productDto.getDescription());
            existing.setPrice(productDto.getPrice());
            existing.setQuantity(productDto.getQuantity());
            existing.setImage(productDto.getImage().getOriginalFilename());
            // existing.setUser(user);
            existing.setCategory(category);

            productService.createProduct(existing);

            String staticFolderPath = "src/main/resources/static/img/product/" + existing.getId() + "/";
            Path folderPath = Paths.get(staticFolderPath);
            try {
                Files.createDirectories(folderPath);
                System.out.println("Folder created successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to create folder.");
            }

            Path path = Paths.get(staticFolderPath + existing.getImage());
            Files.write(path, productDto.getImage().getBytes());

            res.put("code", 0);
            res.put("message", "Cập nhật sản phẩm thành công");
        } else {
            res.put("code", 1);
            res.put("message", "Không tìm thấy id sản phẩm này");
        }
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        Optional<Product> optional = productService.getProductById(id);

        if (optional.isPresent()) {
            productService.deleteProduct(id);
            res.put("code", 0);
            res.put("message", "Xóa sản phẩm thành công");
        } else {
            res.put("code", 1);
            res.put("message", "Xóa sản phẩm thất bại");
        }
        return ResponseEntity.ok(res);
    }

    // @GetMapping("/search") // tìm kiếm tên theo keyword
    // public ResponseEntity<?> searchUser(@RequestParam(name = "keyword", required
    // = false) String name) {
    // List<UserDto> users = userService.searchUser(name);
    // return ResponseEntity.ok(users);
    // }
}
