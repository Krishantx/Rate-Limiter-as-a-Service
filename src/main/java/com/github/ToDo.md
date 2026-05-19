# Rate Limiting as a Service TODO


## Phase 2 - Core API
- [ ] Create /check endpoint
- [ ] Create request DTO
- [ ] Create response DTO
- [ ] Add validation

---

## Phase 3 - In-Memory Rate Limiter
- [ ] Create RateLimiter interface
- [ ] Implement Fixed Window algorithm
- [ ] Add expiry logic
- [ ] Test with Postman

---

## Phase 4 - Redis
- [ ] Install Redis
- [ ] Connect Redis to Spring Boot
- [ ] Replace HashMap with Redis
- [ ] Add TTL support

---

## Bugs
- [ ] Expiry resets incorrectly
- [ ] Concurrent requests issue

---

## Improvements
- [ ] Add Docker
- [ ] Add Grafana
- [ ] Add dashboard