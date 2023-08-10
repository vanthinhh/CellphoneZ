package com.bienvan.store.controller;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bienvan.store.model.*;
import com.bienvan.store.service.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @GetMapping(value = { "", "/", "/home" })
    public String getHomePage(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        List<Product> ps = productService.getProducts();
        List<Category> categories = categoryService.getAllCategories();
        // int pageNumber = (page > 0) ? (page - 1) : 0; // Điều chỉnh số trang
        // Pageable pageable = PageRequest.of(pageNumber, size);
        // Page<Product> products = productService.findAll(pageable);
        // model.addAttribute("products", ps);
        // model.addAttribute("ps", ps);

        session.setAttribute("products", ps);
        session.setAttribute("categories", categories);

        
        return "index";
    }

    @PostMapping(value = { "", "/", "/home" })
    public String searchProducts(@RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "priceRange", required = false, defaultValue = "0-999999999") String priceRange,
            @RequestParam(name = "category", required = false) Long categoryId,
            @RequestParam(name = "brand", required = false) String brand, HttpSession session) {

        if (color.isEmpty()) {
            color = null;
        }
        if (brand.isEmpty()) {
            brand = null;
        }

        String[] arr = priceRange.split("-");
        Double minPrice = Double.parseDouble(arr[0]);
        Double maxPrice = Double.parseDouble(arr[1]);
        List<Product> products = new ArrayList<>();
        if (categoryId != null) {
            Category category = categoryService.findById(categoryId).orElse(null);
            if (category != null) {
                products = productService.search(minPrice, maxPrice, brand, color, category);
            }
        } else {
            products = productService.search(minPrice, maxPrice, brand, color, null);
        }

        session.setAttribute("products", products);

        return "index";
    }

    @GetMapping(value = { "/profile" })
    public String getProfilePage(Model model) {
        // List<UserDto> userList = userService.getAllUsers();
        // model.addAttribute("users", userList);
        return "profile";
    }

    @GetMapping(value = { "/product-detail/{id}" })
    public String getProductDetailPage(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id).orElse(null);

        if (product != null) {
            model.addAttribute("product", product);
            return "product-detail";
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
            // Trở về trang chính hoặc trang thông báo lỗi tùy ý
            return "redirect:/";
        }
    }

    // @GetMapping(value = { "/cart" })
    // public String getCartPage(Model model) {
    // // List<UserDto> userList = userService.getAllUsers();
    // // model.addAttribute("users", userList);
    // return "cart";
    // }

}
