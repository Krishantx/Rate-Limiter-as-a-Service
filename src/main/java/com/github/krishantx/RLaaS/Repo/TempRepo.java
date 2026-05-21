package com.github.krishantx.RLaaS.Repo;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.github.krishantx.Model.TokenBucket;

@Repository
public class TempRepo {
    /* Key - Identifier,
    Value = {
        "tokens" : (int) x,
        "lastRequest" : (timestamp in s) y        
    }*/ 
    HashMap<String, TokenBucket> map;

    public TempRepo() {
        map = new HashMap<>();
    }

    public TokenBucket getIdentifier(String identifier) {
        return map.get(identifier);
    }
    public void putObject(String identifier, TokenBucket tokenBucket) {
        map.put(identifier, tokenBucket);
    }
    
    
}
