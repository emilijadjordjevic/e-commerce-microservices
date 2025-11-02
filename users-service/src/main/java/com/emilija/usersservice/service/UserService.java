package com.emilija.usersservice.service;

import com.emilija.usersservice.dto.UserDto;
import com.emilija.usersservice.dto.UserRequest;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto findById(Long id);
    UserDto create(UserRequest req);
    UserDto update(Long id, UserRequest req);
    void delete(Long id);
    void deductBalance(Long id, Double amount);
}
