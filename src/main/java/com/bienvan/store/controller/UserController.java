package com.bienvan.store.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bienvan.store.dto.UserDto;
import com.bienvan.store.model.ERole;
import com.bienvan.store.model.Role;
import com.bienvan.store.model.User;
import com.bienvan.store.model.mapper.UserMapper;
import com.bienvan.store.service.ProductService;
import com.bienvan.store.service.RoleService;
import com.bienvan.store.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    RoleService roleService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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
            String encodePW = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePW);
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
            for (Role r : info.getUserRoles()) {
                session.setAttribute(r.getName().toString(), r.getName().toString());
                System.out.println(r.getName().toString());
            }
            session.setAttribute("id", info.getId());
            session.setAttribute("email", email);
            session.setAttribute("name", info.getName());
            session.setAttribute("gender", info.getGender());

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

            String encodePW = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePW);
            
            Role role = roleService.findByName(ERole.ROLE_USER).get();
            Set<Role> listRoles = new HashSet<Role>();
            
            listRoles.add(role);
            user.setUserRoles(listRoles);
            User newUser = userService.createUser(user);

            for (Role r : listRoles) {
                session.setAttribute(r.getName().toString(), r.getName().toString());
            }
            
            session.setAttribute("id", newUser.getId());
            session.setAttribute("email", newUser.getEmail());
            session.setAttribute("name", newUser.getName());
            session.setAttribute("gender", newUser.getGender());

            

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
        session.invalidate();
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("message", "Đăng xuất thành công");
        return ResponseEntity.ok(res);
    }
}
