package com.github.krishantx.RLaaS.Repo;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import  com.github.krishantx.Model.TokenBucket;

@Repository
public class RedisRepo {
    private final RedisTemplate<String, TokenBucket> redisTemplate;

    public RedisRepo(RedisTemplate<String, TokenBucket> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public TokenBucket get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void save(String key, TokenBucket value) {
        redisTemplate.opsForValue().set(key, value);
    }

}
