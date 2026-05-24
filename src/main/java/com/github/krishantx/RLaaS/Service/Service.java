package com.github.krishantx.RLaaS.Service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.github.krishantx.RLaaS.Model.CheckDTO;
import com.github.krishantx.RLaaS.Model.DatabaseModels.ClientEntity;
import com.github.krishantx.RLaaS.Model.DatabaseModels.EndpointEntity;
import com.github.krishantx.RLaaS.Model.TokenBucket;
import com.github.krishantx.RLaaS.Repo.ClientRepo;
import com.github.krishantx.RLaaS.Repo.PostgresRepo;
import com.github.krishantx.RLaaS.Repo.RedisRepo;

@org.springframework.stereotype.Service
public class Service {
    @Autowired RedisRepo redisService;
    @Autowired ClientRepo clientRepo;
    @Autowired PostgresRepo postgresRepo;

    @Async
    public void modifyCache(String key, String apiKey, String endpoint) {
        Optional<ClientEntity> client = clientRepo.findByApiKey(apiKey);
        if (client.isEmpty()) { return; }

        Optional<EndpointEntity> endpointModel = 
            postgresRepo
                .findByEndpointAndClient(
                    endpoint, 
                    client.get()
                );

        if (endpointModel.isEmpty()){ return; }

        int rateLimit = endpointModel
                    .get()
                    .getRateLimit();


        TokenBucket tokenBucket = 
            new TokenBucket(rateLimit, Instant.now().getEpochSecond());
        redisService.save(key, tokenBucket, 1);
    }

    public boolean check(CheckDTO requestDTO, String apiKey) {
        //Create Redis Key;
        String key = RedisRepo.createRedisKey(
            apiKey,
            requestDTO.getMethod(),
            requestDTO.getIdentifier(),
            requestDTO.getEndpoint()
        );

        int ttl = 5; //Hard coded need to change by database fetching.
        TokenBucket tokenBucket = redisService.get(key);

        //For cache miss:
        if (tokenBucket == null) {
            modifyCache(
                key, 
                apiKey, 
                requestDTO.getEndpoint()
            );
            return false;
        }

        //For cache hit:
        else {
            TokenBucket newTokenBucket;
            long timeDifference = (Instant.now().getEpochSecond() - tokenBucket.getTimestamp());
            int rateLimit = tokenBucket.getRateLimit();
            float totalTokens =(((float)rateLimit/60) * timeDifference) + tokenBucket.getTokens();
            totalTokens = Math.min(totalTokens, rateLimit);

            if (totalTokens >= 1) {
                newTokenBucket = 
                    new TokenBucket(totalTokens-1, Instant.now().getEpochSecond());
                redisService.save(requestDTO.getIdentifier(), newTokenBucket, ttl);
                return false;
            } else{
                newTokenBucket = new TokenBucket(totalTokens, Instant.now().getEpochSecond());
                redisService.save(requestDTO.getIdentifier(), newTokenBucket, ttl);
                return true;
            }
        }       
    }
}
