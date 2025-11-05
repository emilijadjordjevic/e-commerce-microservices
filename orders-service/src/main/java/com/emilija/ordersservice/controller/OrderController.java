package com.emilija.ordersservice.controller;

import com.emilija.ordersservice.dto.OrderDetailsDTO;
import com.emilija.ordersservice.dto.OrderDTO;
import com.emilija.ordersservice.dto.OrderRequest;
import com.emilija.ordersservice.service.ConcreteOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final ConcreteOrderService service;

    public OrderController(ConcreteOrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderDTO> all() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderRequest request) {
        var created = service.createFromRequest(request);
        return ResponseEntity.created(URI.create("/api/orders/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<OrderDetailsDTO> getDetails(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderDetails(id));
    }

    @GetMapping("/user/{userId}")
    public List<OrderDetailsDTO> getByUser(@PathVariable Long userId) {
        return service.getOrdersByUserId(userId);
    }

}
