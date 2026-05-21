package com.github.krishantx.RLaaS.Service;


import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.krishantx.Model.RequestDTO;
import com.github.krishantx.Model.TokenBucket;
import com.github.krishantx.RLaaS.Repo.TempRepo;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    TempRepo tempRepo;
    public boolean check(RequestDTO requestDTO) {
        TokenBucket tokenBucket = tempRepo.getIdentifier(requestDTO.getIdentifier());
        if (tokenBucket == null) {
            // The request is brand new and a new entity needs to be created
            tokenBucket = new TokenBucket();
            tokenBucket.setTokens(20);
            tokenBucket.setTimestamp(Instant.now().getEpochSecond());
            System.out.println(tokenBucket);
            return false;
        }

        else {
            // Check if rate needs to be limited
            long timeDifference = (Instant.now().getEpochSecond() - tokenBucket.getTimestamp());
            int rateLimit = 20;
            long newTokens =(rateLimit/60) * timeDifference;
            long totalTokens = (tokenBucket.getTokens() + newTokens);
            if (totalTokens > 1) {
                tempRepo.putObject(
                    requestDTO.getIdentifier(),
                    new TokenBucket((int)totalTokens - 1, Instant.now().getEpochSecond())
                );
                return false;
            } else{
                return true;
            }
        }       
    }
}
