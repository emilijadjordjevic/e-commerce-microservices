package com.emilija.productsservice.controller;

import com.emilija.productsservice.dto.ProductDTO;
import com.emilija.productsservice.dto.ProductRequest;
import com.emilija.productsservice.mapper.ProductMapper;
import com.emilija.productsservice.service.ConcreteProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ConcreteProductService service;

    public ProductController(ConcreteProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductDTO> all() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductRequest request) {
        var created = service.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        ProductDTO updated = null;
        try {
            updated = service.update(id, request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/reduce-stock")
    public void reduceStock(@PathVariable Long id, @RequestParam Integer quantity) {
        service.reduceStock(id, quantity);
    }

    @PostMapping("/{id}/add-stock")
    public void addStock(@PathVariable Long id, @RequestParam Integer quantity) {
        service.addStock(id, quantity);
    }

}
