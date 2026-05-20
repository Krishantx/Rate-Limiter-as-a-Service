# Rate Limiting as a Service TODO


## Phase 1 - Core API
- [ ] Create /check endpoint
- [ ] Create request DTO
- [ ] Create response DTO
- [ ] Add validation
Create a single client bare bone version
Flow: A client recieves a HTTP request. It does not process it right away. The request is forwarded to the rate limiter with the following body.
{
    "identifier/ip" : the ip,
    
}

---

## Phase 2 - In-Memory Rate Limiter
- [ ] Create RateLimiter interface
- [ ] Implement Fixed Window algorithm
- [ ] Add expiry logic
- [ ] Test with Postman

---

## Phase 3 - Redis
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