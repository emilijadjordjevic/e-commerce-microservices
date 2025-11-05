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

1. **Eureka Server**
2. **Users Service**
3. **Products Service**
4. **Orders Service**
5. (Opcionalno) **API Gateway**