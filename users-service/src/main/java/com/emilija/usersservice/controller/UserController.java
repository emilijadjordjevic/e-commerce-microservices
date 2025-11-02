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
    private final UserMapper mapper;

    public UserController(ConcreteUserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<UserDto> all() {
        return service.findAll().stream().map(mapper::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserRequest request) {
        var created = service.createFromRequest(request);
        var dto = mapper.toDto(created);
        return ResponseEntity.created(URI.create("/api/users/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        return service.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        var updated = service.updateFromRequest(id, request);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
