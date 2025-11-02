package com.emilija.ordersservice.mapper;

import com.emilija.ordersservice.dto.OrderDTO;
import com.emilija.ordersservice.dto.OrderRequest;
import com.emilija.ordersservice.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDTO toDto(Order order) {
        return new OrderDTO(order.getId(), order.getUserId(), order.getProductId(), order.getQuantity(), order.getTotalAmount());
    }

    public Order fromRequest(OrderRequest request) {
        return Order.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();
    }
}