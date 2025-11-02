package com.emilija.productsservice.mapper;

import com.emilija.productsservice.dto.ProductDTO;
import com.emilija.productsservice.dto.ProductRequest;
import com.emilija.productsservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toDto(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice());
    }

    public Product fromRequest(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }
}