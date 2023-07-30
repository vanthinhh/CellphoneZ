package com.bienvan.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.bienvan.store.model.*;
import com.bienvan.store.model.dto.UserDto;
import com.bienvan.store.service.*;

@Controller
public class AdminController {
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

    @GetMapping(value = { "/admin/dashboard", "/admin" })
    public String getAdminPage() {
        return "/admin/dashboard";
    }

    @GetMapping(value = { "/test" })
    public String test() {
        return "/admin/test";
    }

    @GetMapping(value = { "/admin/user-manager" })
    public String getUserManager(ModelMap model) {
        List<UserDto> list = userService.getAllUsers();

        model.addAttribute("users", list);
        return "/admin/user-manager";
    }

    @GetMapping(value = { "/admin/category-manager" })
    public String getCategoryManager(ModelMap model) {
        List<Category> list = categoryService.getAllCategories();

        model.addAttribute("categories", list);
        return "/admin/category-manager";
    }

    @GetMapping(value = { "/admin/product-manager" })
    public String getProductManager(ModelMap model) {
        List<Product> list = productService.getProducts();
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("products", list);
        model.addAttribute("categories", categories);
        return "/admin/product-manager";
    }

    @GetMapping(value = { "/admin/order-manager" })
    public String getOrderManager(ModelMap model) {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        List<Order> orders = orderService.getAllOrders();

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("orders", orders);
        return "/admin/order-manager";
    }


}
