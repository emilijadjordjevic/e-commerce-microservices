package com.emilija.usersservice.mapper;

import org.springframework.stereotype.Component;
import com.emilija.usersservice.entity.User;
import com.emilija.usersservice.dto.UserDto;
import com.emilija.usersservice.dto.UserRequest;

@Component
public class UserMapper {

    public UserDto toDto(User u) {
        if (u == null) return null;
        return new UserDto(u.getId(), u.getName(), u.getEmail());
    }

    public User toEntity(UserRequest r) {
        if (r == null) return null;
        return User.builder()
                .name(r.getName())
                .email(r.getEmail())
                .build();
    }

    public void updateEntityFromRequest(UserRequest req, User existing) {
        if (req == null || existing == null) return;
        existing.setName(req.getName());
        existing.setEmail(req.getEmail());
    }
}
