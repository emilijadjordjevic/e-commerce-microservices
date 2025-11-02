package com.emilija.ordersservice.client;

import com.emilija.ordersservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "users-service")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserDTO getUser(@PathVariable Long id);

    @PatchMapping("/api/users/{id}/deduct-balance")
    void deductBalance(@PathVariable Long id, @RequestParam Double amount);
}