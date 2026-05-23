package com.github.krishantx.RLaaS.Service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.krishantx.Model.RequestDTO;
import com.github.krishantx.Model.TokenBucket;
import com.github.krishantx.RLaaS.Repo.RedisRepo;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    RedisRepo redisService;
    public boolean check(RequestDTO requestDTO) {
        System.out.println(redisService.get(requestDTO.getIdentifier()));
        TokenBucket tokenBucket = redisService.get(requestDTO.getIdentifier());
        System.out.println(tokenBucket);
        if (tokenBucket == null) {
            tokenBucket = new TokenBucket(3, Instant.now().getEpochSecond());
            redisService.save(requestDTO.getIdentifier(), tokenBucket);
            return false;
        }

        else {
            TokenBucket newTokenBucket;
            long timeDifference = (Instant.now().getEpochSecond() - tokenBucket.getTimestamp());
            int rateLimit = 20;
            float totalTokens =(((float)rateLimit/60) * timeDifference) + tokenBucket.getTokens();
            totalTokens = Math.min(totalTokens, rateLimit);
            if (totalTokens >= 1) {

                newTokenBucket = 
                    new TokenBucket(
                        totalTokens-1, 
                        Instant.now().getEpochSecond()
                    );

                redisService.save(requestDTO.getIdentifier(), newTokenBucket);
                return false;
            } else{

                newTokenBucket = new TokenBucket(
                    totalTokens, 
                    Instant.now().getEpochSecond()
                );

                redisService.save(
                    requestDTO.getIdentifier(), 
                    newTokenBucket
                );

                return true;
            }
        }       
    }
}
