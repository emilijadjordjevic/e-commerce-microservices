package com.emilija.usersservice.mapper;

import org.springframework.stereotype.Component;
import com.emilija.usersservice.entity.User;
import com.emilija.usersservice.dto.UserDto;
import com.emilija.usersservice.dto.UserRequest;

@Component
public class UserMapper {

    public UserDto toDto(User u) {
        if (u == null) return null;
        return new UserDto(u.getId(), u.getName(), u.getEmail(), u.getBalance());
    }

//    public User toEntity(UserRequest r) {
//        if (r == null) return null;
//        return User.builder()
//                .name(r.getName())
//                .email(r.getEmail())
//                .balance(r.getBalance())
//                .build();
//    }

    public User fromRequest(UserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .balance(request.getBalance())
                .build();
    }
}
