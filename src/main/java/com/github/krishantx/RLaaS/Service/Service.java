package com.github.krishantx.RLaaS.Service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.github.krishantx.RLaaS.Model.CheckDTO;
import com.github.krishantx.RLaaS.Model.DatabaseModels.ClientEntity;
import com.github.krishantx.RLaaS.Model.DatabaseModels.EndpointEntity;
import com.github.krishantx.RLaaS.Model.TokenBucket;
import com.github.krishantx.RLaaS.Repo.ClientRepo;
import com.github.krishantx.RLaaS.Repo.PostgresRepo;
import com.github.krishantx.RLaaS.Repo.RedisRepo;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    RedisRepo redisService;

    @Autowired
    ApiKeyService apiKeyService;

    @Autowired
    PostgresRepo postgresRepo;
    @Autowired
    ClientRepo clientRepo;
    public ResponseEntity<?> addEndpoint(String endpoint, int rateLimit, String apiKey) {
        EndpointEntity endpointEntity = new EndpointEntity(endpoint, rateLimit);
        Optional<ClientEntity> client = clientRepo.findByApiKey(apiKey);

        if (client.isEmpty()) 
            return ResponseEntity.status(404).body("Incorrect API Key");

        Optional<EndpointEntity> fetchFromDB = postgresRepo.findByEndpointAndClient(endpoint, client.get());
        
        if (fetchFromDB.isEmpty()) {
            endpointEntity.setClient(client.get());
            
            postgresRepo.save(endpointEntity);
            return ResponseEntity.status(200).build();
        }
        else
            return ResponseEntity.status(409).build();
    }

    public boolean check(CheckDTO requestDTO) {
        long ttl = 5;
        System.out.println(redisService.get(requestDTO.getIdentifier()));
        TokenBucket tokenBucket = redisService.get(requestDTO.getIdentifier());
        System.out.println(tokenBucket);
        if (tokenBucket == null) {
            tokenBucket = new TokenBucket(3, Instant.now().getEpochSecond());
            redisService.save(requestDTO.getIdentifier(), tokenBucket, ttl);
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

                redisService.save(
                    requestDTO.getIdentifier(), 
                    newTokenBucket,
                    ttl
                );

                return false;
            } else{

                newTokenBucket = new TokenBucket(
                    totalTokens, 
                    Instant.now().getEpochSecond()
                );

                redisService.save(
                    requestDTO.getIdentifier(), 
                    newTokenBucket,
                    ttl
                );

                return true;
            }
        }       
    }

    public ClientEntity createClient(ClientEntity clientEntity) {
        if (clientRepo.findByClientName(clientEntity.getClientName()).isEmpty()) {
            String apiKey = apiKeyService.generateApiKey();
            clientEntity.setApiKey(apiKey);
            ClientEntity newClientEntity = clientRepo.save(clientEntity);
            return newClientEntity;
        }
        else {
            return null;
        }
    }
}
