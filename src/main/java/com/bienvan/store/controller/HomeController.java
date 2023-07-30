package com.bienvan.store.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bienvan.store.model.*;
import com.bienvan.store.model.dto.UserDto;
import com.bienvan.store.service.ProductService;
import com.bienvan.store.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping(value = { "", "/", "/home" })
    public String getHomePage(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping(value = { "/profile" })
    public String getProfilePage(Model model) {
        // List<UserDto> userList = userService.getAllUsers();
        // model.addAttribute("users", userList);
        return "profile";
    }

    @GetMapping(value = { "/cart" })
    public String getCartPage(Model model) {
        // List<UserDto> userList = userService.getAllUsers();
        // model.addAttribute("users", userList);
        return "cart";
    }

}
