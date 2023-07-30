package com.bienvan.store.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.bienvan.store.model.*;
import com.bienvan.store.model.dto.UserDto;
import com.bienvan.store.model.mapper.UserMapper;
import com.bienvan.store.service.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping // get all user
    public ResponseEntity<?> getListUser() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}") // get 1 user
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            UserDto userDto = UserMapper.toUserDto(userOptional.get());
            res.put("code", 0);
            res.put("message", "Find user successfully");
            res.put("data", userDto);
        } else {
            res.put("code", 1);
            res.put("message", "Find user failed");
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping // tạo user mới
    public ResponseEntity<?> createUser(@RequestBody @Valid User user,
            BindingResult bindingResult) {
        Map<String, Object> res = new HashMap<>();

        if (bindingResult.hasErrors()) {
            res.put("code", 1);
            res.put("message", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.ok(res);
        }

        Optional<User> userOptional = userService.findByEmail(user.getEmail());
        if (!userOptional.isPresent()) {
            User newUser = userService.createUser(user);
            res.put("code", 0);
            res.put("message", "Add user successfully");
            res.put("data", newUser);
        } else {
            res.put("code", 1);
            res.put("message", "Email đã tồn tại");
        }
        return ResponseEntity.ok(res);
    }

    @PutMapping // update
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody @Valid UserDto userDto,
            BindingResult bindingResult) {
        Map<String, Object> res = new HashMap<>();

        if (bindingResult.hasErrors()) {
            res.put("code", 1);
            res.put("message", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.ok(res);
        }

        // kiểm tra xem người dùng có tồn tại hay không
        Optional<User> optionalUser = userService.getUserById(userDto.getId());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setEmail(userDto.getEmail());
            existingUser.setName(userDto.getName());
            existingUser.setGender(userDto.getGender());
            existingUser.setRole(userDto.getRole());

            userService.createUser(existingUser);
            res.put("code", 0);
            res.put("message", "Update user successfully");
        } else {
            res.put("code", 1);
            res.put("message", "Update user failed");
        }
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}") // xóa user
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            userService.deleteUser(id);
            res.put("code", 0);
            res.put("message", "Delete user successfully");
        } else {
            res.put("code", 1);
            res.put("message", "Delete user failed");
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping(value = { "/login" })
    public ResponseEntity<?> checkLogin(@RequestParam("email") String email, @RequestParam("password") String password,
            HttpSession session) {
        Map<String, Object> res = new HashMap<>();
        if (userService.checkLogin(email, password)) {
            User info = userService.findByEmail(email).get();
            session.setAttribute("id", info.getId());
            session.setAttribute("email", email);
            session.setAttribute("name", info.getName());
            session.setAttribute("gender", info.getGender());
            session.setAttribute("role", info.getRole());

            res.put("code", 0);
            res.put("message", "Đăng nhập thành công");
        } else {
            res.put("code", 1);
            res.put("message", "Email hoặc mật khẩu không chính xác");
        }

        return ResponseEntity.ok(res);
    }

    @PostMapping(value = { "/register" })
    public ResponseEntity<?> register(@ModelAttribute @Valid User user,
            BindingResult bindingResult,
            HttpSession session) {
        Map<String, Object> res = new HashMap<>();
        if (bindingResult.hasErrors()) {
            res.put("code", 1);
            res.put("message", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.ok(res);
        }
        if (!userService.findByEmail(user.getEmail()).isPresent()) {
            user.setRole("customer");
            User newUser = userService.createUser(user);
            
            session.setAttribute("id", newUser.getId());
            session.setAttribute("email", newUser.getEmail());
            session.setAttribute("name", newUser.getName());
            session.setAttribute("gender", newUser.getGender());
            session.setAttribute("role", newUser.getRole());

            res.put("code", 0);
            res.put("message", "Đăng ký thành công");
        } else {
            res.put("code", 1);
            res.put("message", "Email đã tồn tại");
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("name");
        session.removeAttribute("gender");
        session.removeAttribute("role");
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("message", "Đăng xuất thành công");
        return ResponseEntity.ok(res);
    }
}
