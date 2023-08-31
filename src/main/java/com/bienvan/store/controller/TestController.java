package com.bienvan.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bienvan.store.model.Brand;
import com.bienvan.store.model.Color;
import com.bienvan.store.service.BrandService;
import com.bienvan.store.service.ColorService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    BrandService brandService;

    @Autowired
    ColorService colorService;

    @GetMapping("")
    public String showAdmin(HttpSession session) {
        List<Brand> brands = brandService.getBrands();
        List<Color> colors = colorService.getColors();

        session.setAttribute("brands", brands);
        return "test";
    }

    @GetMapping("/user")
    public String showUserPage() {
        return "index";
    }
}
