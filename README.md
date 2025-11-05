## 🧱 Arhitektura

- **Eureka Service (Service Discovery)**
- **Users Service** – evidencija korisnika i njihovog balansa
- **Products Service** – evidencija proizvoda i dostupne količine
- **Orders Service** – kreiranje porudžbina (komunikacija sa Users i Products)
- (Opcioni) **API Gateway** – jedinstvena ulazna tačka

Komunikacija između servisa se vrši preko **OpenFeign**.
Otpornost sistema obezbeđena je korišćenjem **Resilience4j** (Circuit Breaker + Retry).

## ▶️ Pokretanje

Pokretanje u ispravnom redosledu:

1. **Eureka Server** →  `http://localhost:8761`
2. **Users Service** →  `http://localhost:8081`
3. **Products Service** →  `http://localhost:8082`
4. **Orders Service** →  `http://localhost:8083`
5. **API Gateway** (opciono) →  `http://localhost:8080`

## REST API Endpoints

| Servis           | Endpoint Base URL                    | Swagger UI                           |
|------------------|--------------------------------------|-------------------------------------|
| users-service    | `http://localhost:8081/api/users`    | `http://localhost:8081/swagger-ui.html` |
| products-service | `http://localhost:8082/api/products` | `http://localhost:8082/swagger-ui.html` |
| orders-service   | `http://localhost:8083/api/orders`   | `http://localhost:8083/swagger-ui.html` |

## Architecture Diagrams and Screenshots

### Feign Flow
<p float="left">
  <img src="images/feign-call.png" width="200"/>
  <img src="images/feign-proxy.png" width="200"/>
  <img src="images/feign-proxy-detail.png" width="200"/>
</p>

### Gateway
<p float="left">
  <img src="images/gateway.png" width="200"/>
  <img src="images/gateway-load_balancer.png" width="200"/>
</p>

### APIs
#### User API
![User API](images/user-api.png)

#### Product API
![Product API](images/product-api.png)

#### Order API
![Order API](images/order-api.png)

#### Screenshot
![Screenshot](images/Screenshot from 2025-11-05 09-55-39.png)
