package com.emilija.usersservice.service;

import com.emilija.usersservice.exception.NotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emilija.usersservice.entity.User;
import com.emilija.usersservice.repository.UserRepository;
import com.emilija.usersservice.dto.UserRequest;
import com.emilija.usersservice.exception.ConflictException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConcreteUserService implements UserService {
    private final UserRepository repo;

    public ConcreteUserService(UserRepository repo) {
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
            throw new ConflictException("Email already exists");
        });
        return repo.save(user);
    }

    // helper used by controller
    public User createFromRequest(UserRequest req) {
        User u = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .build();
        return create(u);
    }

    public User update(Long id, User changes) {
        return repo.findById(id).map(existing -> {
            existing.setName(changes.getName());
            existing.setEmail(changes.getEmail());
            return repo.save(existing);
        }).orElseThrow(() -> new NotFound("User not found"));
    }

    public User updateFromRequest(Long id, UserRequest req) {
        return repo.findById(id).map(existing -> {
            existing.setName(req.getName());
            existing.setEmail(req.getEmail());
            return repo.save(existing);
        }).orElseThrow(() -> new NotFound("User not found"));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFound("User not found");
        }
        repo.deleteById(id);
    }
}
