package com.emilija.ordersservice.client;

import com.emilija.ordersservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "users-service")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserDTO getUser(@PathVariable Long id);

    @PostMapping("/api/users/{id}/deduct-balance")
    void deductBalance(@PathVariable Long id, @RequestParam Double amount);
}