package com.emilija.productsservice.service;

import com.emilija.productsservice.dto.ProductDTO;
import com.emilija.productsservice.dto.ProductRequest; // Assuming this exists, based on your code

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> findAll();
    Optional<ProductDTO> findById(Long id);
    ProductDTO create(ProductRequest req);
    ProductDTO update(Long id, ProductRequest req) throws Exception;
    void delete(Long id);
}