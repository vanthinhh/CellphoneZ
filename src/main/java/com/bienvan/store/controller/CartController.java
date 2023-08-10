package com.bienvan.store.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.bienvan.store.model.*;
import com.bienvan.store.service.CategoryService;
import com.bienvan.store.service.OrderItemService;
import com.bienvan.store.service.OrderService;
import com.bienvan.store.service.ProductService;
import com.bienvan.store.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
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

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart);
        List<Product> products = productService.getProducts();

        Map<Long, Product> productMap = new HashMap<>();

        for (Product product : products) {
            productMap.put(product.getId(), product);
        }
        model.addAttribute("productMap", productMap);

        return "cart";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(Product item, HttpSession session) {
        Map<String, Object> res = new HashMap<>();

        Product product = productService.getProductById(item.getId()).get();
        if (product != null) {
            product.setQuantity(item.getQuantity());
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }
            cart.addItem(product);
            session.setAttribute("cart", cart);
            res.put("code", 0);
            res.put("message", "Thêm sản phẩm vào giỏ hàng thành công");
        } else {
            // @ModelAttribute("item")
            res.put("code", 1);
            res.put("message", "Thêm sản phẩm vào giỏ hàng thất bại");
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/delete/{id}")
    public String deleteInCart(@PathVariable Long id, HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        cart.removeItem(id);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody List<Product> cartItemUpdates, HttpSession session) {
        Map<String, Object> res = new HashMap<>();
        Cart cart = (Cart) session.getAttribute("cart");
        for (Product update : cartItemUpdates) {
            Long productId = update.getId();
            int newQuantity = update.getQuantity();

            Product product = cart.getItemById(productId);
            if (product != null) {
                if (newQuantity > 0) {
                    product.setQuantity(newQuantity);
                } else {
                    // cart.removeItem(productId);
                }
            }
        }
        res.put("code", 0);
        res.put("message", "Cập nhật giỏ hàng thành công");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/pay")
    public String payCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");

        LocalDateTime lt = LocalDateTime.now();

        Order order = new Order();
        order.setStatus("Pending");
        order.setPhone("0123456789");
        order.setAddress("Saigon");
        order.setCreate_at(lt.toString());
        order.setTotal(cart.calculateTotalPrice());

        orderService.createOrder(order);
        // Create an OrderItem
        for (Map.Entry<Long, Product> entry : cart.getCartItems().entrySet()) {
            Long key = entry.getKey();
            Product value = entry.getValue();
            OrderItem orderItem = new OrderItem();
            orderItem.setPurchase_price(value.getPrice());
            orderItem.setQuantity(value.getQuantity());

            Product product = productService.getProductById(key).get();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItemService.createOrderItem(orderItem);
        }

        List<Order> orders = orderService.getAllOrders();
        
        model.addAttribute("orders", orders);
        
        session.removeAttribute("cart");

        return "order-check";
    }

    @GetMapping("/check")
    public String checkCart(HttpSession session, Model model) {
        
        List<Order> orders = orderService.getAllOrders();
        
        model.addAttribute("orders", orders);
        
        return "order-check";
    }
}
