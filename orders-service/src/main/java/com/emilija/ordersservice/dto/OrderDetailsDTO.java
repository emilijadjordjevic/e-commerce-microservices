package com.emilija.ordersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {
    private Long orderId;
    private Integer quantity;
    private Double totalAmount;
    private UserDTO user;
    private ProductDTO product;
}