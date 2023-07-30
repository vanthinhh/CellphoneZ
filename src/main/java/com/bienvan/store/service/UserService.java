package com.bienvan.store.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bienvan.store.model.*;
import com.bienvan.store.model.dto.UserDto;
import com.bienvan.store.model.mapper.UserMapper;
import com.bienvan.store.repository.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(User order) {
        return userRepository.save(order);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<UserDto>();
        if(!users.isEmpty()){
            for(User u: users){
                UserDto tmp = UserMapper.toUserDto(u);
                userDtos.add(tmp);
            }
            return userDtos;
        }
        return null;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean checkLogin(String email, String password){
        Optional<User> optional = findByEmail(email);
        if(optional.isPresent() && optional.get().getPassword().equals(password)){
            return true;
        }
        return false;
    }
}
