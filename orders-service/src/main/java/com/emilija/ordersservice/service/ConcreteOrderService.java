package com.emilija.ordersservice.service;

import com.emilija.ordersservice.client.ProductClient;
import com.emilija.ordersservice.client.UserClient;
import com.emilija.ordersservice.dto.OrderDTO;
import com.emilija.ordersservice.dto.OrderDetailsDTO;
import com.emilija.ordersservice.dto.OrderRequest;
import com.emilija.ordersservice.dto.UserDTO;
import com.emilija.ordersservice.dto.ProductDTO;
import com.emilija.ordersservice.entity.Order;
import com.emilija.ordersservice.exception.InsufficientBalanceException;
import com.emilija.ordersservice.exception.NotFound;
import com.emilija.ordersservice.mapper.OrderMapper;
import com.emilija.ordersservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConcreteOrderService implements OrderService {
    private final OrderRepository repo;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;

    public ConcreteOrderService(OrderRepository repo, UserClient userClient, ProductClient productClient, OrderMapper mapper) {
        this.repo = repo;
        this.userClient = userClient;
        this.productClient = productClient;
        this.mapper = mapper;
    }

    @Override
    public List<OrderDTO> findAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDTO> findById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public OrderDTO createFromRequest(OrderRequest req) {
        UserDTO user = getUserProtected(req.getUserId());
        if (user == null) throw new NotFound("User not found");

        ProductDTO product = getProductProtected(req.getProductId());
        if (product == null) throw new NotFound("Product not found");

        Double total = product.getPrice() * req.getQuantity();
        if (user.getBalance() < total) throw new InsufficientBalanceException("Insufficient balance");

        deductBalanceProtected(req.getUserId(), total);

        Order order = mapper.fromRequest(req);
        order.setTotalAmount(total);
        Order saved = repo.save(order);

        return mapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFound("Order not found");
        }
        repo.deleteById(id);
    }

    @Override
    public OrderDetailsDTO getOrderDetails(Long id) {
        Order order = repo.findById(id).orElseThrow(() -> new NotFound("Order not found"));
        UserDTO user = getUserProtected(order.getUserId());
        ProductDTO product = getProductProtected(order.getProductId());
        return new OrderDetailsDTO(order.getId(), order.getQuantity(), order.getTotalAmount(), user, product);
    }

    // Protected methods for Resilience4j
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    @Retry(name = "userService")
    protected UserDTO getUserProtected(Long id) {
        return userClient.getUser(id);
    }

    private UserDTO getUserFallback(Long id, Throwable t) {
        // Short fallback response or error
        throw new RuntimeException("User service unavailable: " + t.getMessage());
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "deductBalanceFallback")
    @Retry(name = "userService")
    protected void deductBalanceProtected(Long id, Double amount) {
        userClient.deductBalance(id, amount);
    }

    private void deductBalanceFallback(Long id, BigDecimal amount, Throwable t) {
        // Short fallback
        throw new RuntimeException("User service unavailable for deduct: " + t.getMessage());
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "getProductFallback")
    @Retry(name = "productService")
    protected ProductDTO getProductProtected(Long id) {
        return productClient.getProduct(id);
    }

    private ProductDTO getProductFallback(Long id, Throwable t) {
        // Short fallback
        throw new RuntimeException("Product service unavailable: " + t.getMessage());
    }
}