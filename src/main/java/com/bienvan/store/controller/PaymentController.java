package com.bienvan.store.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bienvan.store.model.Cart;
import com.bienvan.store.model.Order;
import com.bienvan.store.model.OrderItem;
import com.bienvan.store.model.Product;
import com.bienvan.store.model.User;
import com.bienvan.store.model.VNPay;
import com.bienvan.store.service.OrderItemService;
import com.bienvan.store.service.OrderService;
import com.bienvan.store.service.ProductService;
import com.bienvan.store.service.UserService;
import com.bienvan.store.service.VNPayService;

@Controller
public class PaymentController {
    public static String tenKH;
    public static String sdtKH;
    public static String address;

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @GetMapping("")
    public String home() {
        return "index";
    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam String name, @RequestParam String phone, @RequestParam String streetname,
            @RequestParam String city, @RequestParam String district, @RequestParam String ward,
            HttpServletRequest request, HttpSession session) {
        if ((Long) session.getAttribute("id") == null) {
            return "404";
        }
        tenKH = name;
        sdtKH = phone;
        address = streetname + ", " + ward + ", " + district + ", " + city;
        Cart cart = (Cart) session.getAttribute("cart");

        int orderTotal = (int) cart.calculateTotalPrice();
        String orderInfo = "Thanh toan don hang ";
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);

        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model, HttpSession session) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 1) {
            Cart cart = (Cart) session.getAttribute("cart");

            User buyer = userService.getUserById((Long) session.getAttribute("id"));

            Order order = new Order();
            order.setStatus("Đang chờ");
            order.setPhone(sdtKH);
            order.setName(tenKH);
            order.setAddress(address);
            order.setCreate_at(new Date(System.currentTimeMillis()));
            order.setTotal(cart.calculateTotalPrice());
            order.setUserId(buyer);
            order.setPayment_method("VNPay");

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

            VNPay vnPay = new VNPay();
            vnPay.setOrder(order);
            vnPay.setPayment_amount((int)cart.calculateTotalPrice());
            vnPay.setPayment_status("OK");
            vnPay.setTransaction_code(transactionId);

        }

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
}
