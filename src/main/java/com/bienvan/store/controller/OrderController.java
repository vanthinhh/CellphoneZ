package com.bienvan.store.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bienvan.store.model.Order;
import com.bienvan.store.payload.response.MessageResponse;
import com.bienvan.store.service.EmailService;
import com.bienvan.store.service.OrderService;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    private EmailService emailService;

    // Here is the code to send the
    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody Order order) throws MessagingException {

        Optional<Order> optional = orderService.getOrderById(order.getId());

        if (optional.isPresent()) {
            Order existingOrder = optional.get();
            existingOrder.setStatus(order.getStatus());

            orderService.createOrder(existingOrder);

            if (order.getStatus().equals("Đang giao")) {
                String to = existingOrder.getUserId().getEmail();
                String subject = "Thông báo đơn hàng #" + existingOrder.getId() + " của quý khách đã được tiếp nhận";
                System.out.println("dang giao");
                emailService.sendDynamicHtmlEmail(to, subject, existingOrder);
            }
            return ResponseEntity.ok(new MessageResponse(0, "Update order success"));

        }
        return ResponseEntity.ok(new MessageResponse(1, "Update order failed"));
    }

}
