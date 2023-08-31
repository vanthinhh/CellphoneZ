package com.bienvan.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.bienvan.store.dto.UserDto;
import com.bienvan.store.model.*;
import com.bienvan.store.service.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    ColorService colorService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @GetMapping(value = { "/dashboard", "" })
    public String getAdminPage(Model model) {
        List<Product> products = productService.getProducts();
        List<Order> orders = orderService.getAllOrders();
        List<UserDto> users = userService.getAllUsers();
        double totalYear = 0;
        int pending = 0;
        int trading = 0;
        int delivered = 0;
        int canceled = 0;

        for (Order o : orders) {
            if (o.getStatus() == "Đang chờ") {
                pending++;
            }
            if (o.getStatus() == "Đang giao") {
                trading++;
            }
            if (o.getStatus().equals("Đã giao")) {
                delivered++;
                totalYear += o.getTotal();
                System.out.println(totalYear);
            }
            if (o.getStatus() == "Đã hủy") {
                canceled++;
            }
        }

        model.addAttribute("totalYear", totalYear);
        model.addAttribute("pending", pending);
        model.addAttribute("trading", trading);
        model.addAttribute("delivered", delivered);
        model.addAttribute("canceled", canceled);
        model.addAttribute("totalUser", users.size());
        model.addAttribute("totalOrder", orders.size());
        model.addAttribute("totalProduct", products.size());

        return "/admin/dashboard";
    }

    @GetMapping(value = { "/test" })
    public String test() {
        return "/admin/test";
    }

    @GetMapping(value = { "/user-manager" })
    public String getUserManager(ModelMap model) {
        List<UserDto> list = userService.getAllUsers();

        model.addAttribute("users", list);
        return "/admin/user-manager";
    }

    @GetMapping(value = { "/category-manager" })
    public String getCategoryManager(ModelMap model) {
        List<Category> list = categoryService.getAllCategories();

        model.addAttribute("categories", list);
        return "/admin/category-manager";
    }

    @GetMapping(value = { "/product-manager" })
    public String getProductManager(ModelMap model) {
        List<Product> list = productService.getProducts();
        List<Category> categories = categoryService.getAllCategories();
        List<Brand> brands = brandService.getBrands();
        List<Color> colors = colorService.getColors();

        model.addAttribute("products", list);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("colors", colors);

        return "/admin/product-manager";
    }

    @GetMapping(value = { "/order-manager" })
    public String getOrderManager(ModelMap model) {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        List<Order> orders = orderService.getAllOrders();

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("orders", orders);
        return "/admin/order-manager";
    }

    @GetMapping("/order-detail/{id}")
    public String orderDetail(@PathVariable Long id, HttpSession session, Model model) {

        Order order = orderService.getOrderById(id).get();

        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(order);

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("order", order);

        return "/admin/order-detail";
    }
}
