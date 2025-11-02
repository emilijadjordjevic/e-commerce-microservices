package com.emilija.usersservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emilija.usersservice.entity.User;
import com.emilija.usersservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    public User create(User user) {
        repo.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email already exists");
        });
        return repo.save(user);
    }

    public User update(Long id, User changes) {
        return repo.findById(id).map(existing -> {
            existing.setName(changes.getName());
            existing.setEmail(changes.getEmail());
            return repo.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
