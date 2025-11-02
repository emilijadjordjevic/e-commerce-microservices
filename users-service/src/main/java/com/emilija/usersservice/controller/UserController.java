package com.emilija.usersservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.emilija.usersservice.dto.UserDto;
import com.emilija.usersservice.dto.UserRequest;
import com.emilija.usersservice.mapper.UserMapper;
import com.emilija.usersservice.service.ConcreteUserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final ConcreteUserService service;

    public UserController(ConcreteUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDto> all() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserRequest request) {
        var dto = service.create(request);
        return ResponseEntity.created(URI.create("/api/users/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deduct-balance")
    public ResponseEntity<Void> deductBalance(@PathVariable Long id, @RequestParam Double amount) {
        service.deductBalance(id, amount);
        return ResponseEntity.ok().build();
    }
}
