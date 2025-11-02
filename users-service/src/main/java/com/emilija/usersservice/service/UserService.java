package com.emilija.usersservice.service;

import com.emilija.usersservice.dto.UserDTO;
import com.emilija.usersservice.dto.UserRequest;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    UserDTO create(UserRequest req);
    UserDTO update(Long id, UserRequest req);
    void delete(Long id);
    void deductBalance(Long id, Double amount);
}
