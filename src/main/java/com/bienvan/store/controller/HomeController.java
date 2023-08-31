package com.bienvan.store.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bienvan.store.model.*;
import com.bienvan.store.service.*;

import javax.servlet.http.HttpSession;

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

    @Autowired
    BrandService brandService;

    @Autowired
    ColorService colorService;

    @GetMapping(value = { "", "/", "/home" })
    public String getHomePage(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        List<Product> ps = productService.getProducts();
        List<Category> categories = categoryService.getAllCategories();
        List<Brand> brands = brandService.getBrands();
        List<Color> colors = colorService.getColors();
        // int pageNumber = (page > 0) ? (page - 1) : 0; // Điều chỉnh số trang
        // Pageable pageable = PageRequest.of(pageNumber, size);
        // Page<Product> products = productService.findAll(pageable);
        // model.addAttribute("products", ps);
        // model.addAttribute("ps", ps);

        session.setAttribute("brands", brands);
        session.setAttribute("colors", colors);
        session.setAttribute("products", ps);
        session.setAttribute("categories", categories);

        return "index";
    }

    @PostMapping(value = { "", "/", "/home" })
    public String searchProducts(@RequestParam(required = false) String color,
            @RequestParam(required = false, defaultValue = "0-999999999") String priceRange,
            @RequestParam(name = "category", required = false) Long categoryId,
            @RequestParam(required = false) String brand, HttpSession session) {
        Brand byBrand = null;
        Color byColor = null;
        if (color.isEmpty()) {
            color = null;
        } else {
            byColor = colorService.findByName(color);
        }
        if (brand.isEmpty()) {
            brand = null;
        } else {
            byBrand = brandService.findByName(brand);
        }

        String[] arr = priceRange.split("-");
        Double minPrice = Double.parseDouble(arr[0]);
        Double maxPrice = Double.parseDouble(arr[1]);
        List<Product> products = new ArrayList<>();
        if (categoryId != null) {
            Category category = categoryService.findById(categoryId).orElse(null);
            if (category != null) {
                products = productService.search(minPrice, maxPrice, byBrand, byColor, category);
            }
        } else {
            products = productService.search(minPrice, maxPrice, byBrand, byColor, null);
        }

        session.setAttribute("products", products);

        return "index";
    }

    @GetMapping(value = { "/profile" })
    public String getProfilePage(Model model, HttpSession session) {
        if ((Long) session.getAttribute("id") == null) {
            return "404";
        }
        // List<UserDto> userList = userService.getAllUsers();
        // model.addAttribute("users", userList);
        return "profile";
    }

    @GetMapping(value = { "/login" })
    public String login(Model model) {
        // List<UserDto> userList = userService.getAllUsers();
        // model.addAttribute("users", userList);
        return "login";
    }

    @GetMapping(value = { "/product-detail/{id}" })
    public String getProductDetailPage(@PathVariable Long id, Model model) {
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
