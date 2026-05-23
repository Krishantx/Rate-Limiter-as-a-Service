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
            tokenBucket = new TokenBucket(3, Instant.now().getEpochSecond());
            tempRepo.putObject(requestDTO.getIdentifier(), tokenBucket);
            return false;
        }

        else {
            TokenBucket newTokenBucket;
            long timeDifference = (Instant.now().getEpochSecond() - tokenBucket.getTimestamp());
            int rateLimit = 3;
            float totalTokens =(((float)rateLimit/60) * timeDifference) + tokenBucket.getTokens();
            if (totalTokens >= 1) {

                newTokenBucket = 
                    new TokenBucket(
                        totalTokens-1, 
                        Instant.now().getEpochSecond()
                    );

                tempRepo.putObject(requestDTO.getIdentifier(), newTokenBucket);
                return false;
            } else{

                newTokenBucket = new TokenBucket(
                    totalTokens, 
                    Instant.now().getEpochSecond()
                );

                tempRepo.putObject(
                    requestDTO.getIdentifier(), 
                    newTokenBucket
                );
                
                return true;
            }
        }       
    }
}
