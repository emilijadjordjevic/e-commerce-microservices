package com.emilija.usersservice.service;

import com.emilija.usersservice.dto.UserRequest;
import com.emilija.usersservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User create(User user);

    User createFromRequest(UserRequest req);

    User update(Long id, User changes);

    User updateFromRequest(Long id, UserRequest req);

    void delete(Long id);
}
