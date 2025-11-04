package com.emilija.productsservice.service;

import com.emilija.productsservice.dto.ProductDTO;
import com.emilija.productsservice.dto.ProductRequest;
import com.emilija.productsservice.entity.Product;
import com.emilija.productsservice.mapper.ProductMapper;
import com.emilija.productsservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConcreteProductService implements ProductService{
    private final ProductRepository repo;
    private final ProductMapper mapper;

    public ConcreteProductService(ProductRepository repo, ProductMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<ProductDTO> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public ProductDTO create(ProductRequest req) {
        Product entity = mapper.fromRequest(req);
        Product saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public ProductDTO update(Long id, ProductRequest req) throws Exception {
        return repo.findById(id)
                .map(existing -> {
                    existing.setName(req.getName());
                    existing.setPrice(req.getPrice());
                    return mapper.toDto(repo.save(existing));
                })
                .orElseThrow(() -> new Exception("Product not found"));
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void reserve(Long id, int quantity, String clientPaymentId) {

    }
}
