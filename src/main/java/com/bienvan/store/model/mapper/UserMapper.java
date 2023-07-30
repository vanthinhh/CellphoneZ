package com.bienvan.store.model.mapper;

import com.bienvan.store.model.User;
import com.bienvan.store.model.dto.UserDto;

public class UserMapper {
    public static UserDto toUserDto(User user){
        UserDto tmp = new UserDto();
        tmp.setId(user.getId());
        tmp.setEmail(user.getEmail());
        tmp.setName(user.getName());
        tmp.setGender(user.getGender());
        tmp.setRole(user.getRole());
        
        return tmp;
    }
}
