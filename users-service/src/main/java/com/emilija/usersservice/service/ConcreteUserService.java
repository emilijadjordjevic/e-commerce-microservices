package com.emilija.usersservice.service;

import com.emilija.usersservice.dto.UserDTO;
import com.emilija.usersservice.entity.User;
import com.emilija.usersservice.exception.ConflictException;
import com.emilija.usersservice.exception.InsufficientBalanceException;
import com.emilija.usersservice.exception.NotFound;
import com.emilija.usersservice.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emilija.usersservice.repository.UserRepository;
import com.emilija.usersservice.dto.UserRequest;

import java.util.List;

@Service
@Transactional
public class ConcreteUserService implements UserService {
    private final UserRepository repo;
    private final UserMapper mapper;

    public ConcreteUserService(UserRepository repo, UserMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<UserDTO> findAll() {
        var users = repo.findAll();
        System.out.println("Users fetched: " + users);
        return users.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public UserDTO findById(Long id) {
        var user = repo.findById(id)
                .orElseThrow(() -> new NotFound("User not found"));
        return mapper.toDto(user);
    }

    @Override
    public UserDTO create(UserRequest req) {
        repo.findByEmail(req.getEmail())
                .ifPresent(u -> { throw new ConflictException("Email already exists"); });
        User user = mapper.fromRequest(req);
        return mapper.toDto(repo.save(user));
    }

    @Override
    public UserDTO update(Long id, UserRequest req) {
        var existing = repo.findById(id)
                .orElseThrow(() -> new NotFound("User not found"));
        existing.setName(req.getName());
        existing.setEmail(req.getEmail());
        return mapper.toDto(repo.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFound("User not found");
        }
        repo.deleteById(id);
    }

    @Override
    public void deductBalance(Long id, Double amount) {
        User user = repo.findById(id)
                .orElseThrow(() -> new NotFound("User not found"));
        if (user.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        user.setBalance(user.getBalance() - amount);
        repo.save(user);
    }

    @Override
    public void refundBalance(Long id, Double amount) {
        User user = repo.findById(id).orElseThrow(() -> new NotFound("User not found"));
        System.out.println("User balance before reundf: " + user.getBalance() + " amount: " + amount);
        user.setBalance(user.getBalance() + amount);
        repo.save(user);
    }
}
