package com.emilija.ordersservice.service;

import com.emilija.ordersservice.dto.OrderDTO;
import com.emilija.ordersservice.dto.OrderDetailsDTO;
import com.emilija.ordersservice.dto.OrderRequest;
import com.emilija.ordersservice.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDTO> findAll();
    Optional<OrderDTO> findById(Long id);
    OrderDTO createFromRequest(OrderRequest req);
    void delete(Long id);
    OrderDetailsDTO getOrderDetails(Long id);
    List<OrderDetailsDTO> getOrdersByUserId(Long userId);
}