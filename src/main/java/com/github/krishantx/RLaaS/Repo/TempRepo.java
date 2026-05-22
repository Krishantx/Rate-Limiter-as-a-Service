package com.github.krishantx.RLaaS.Repo;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.github.krishantx.Model.TokenBucket;

@Repository
public class TempRepo {
    /* Key - Identifier,
    Value = {
        "tokens" : (int) x,
        "lastRequest" : (timestamp in s) y        
    }*/ 
    public final ConcurrentHashMap<String, TokenBucket> map;

    public TempRepo() {
        map = new ConcurrentHashMap<>();
    }

    public TokenBucket getIdentifier(String identifier) {
        System.out.println("Searching ID" + identifier);
        return map.get(identifier);
    }
    public void putObject(String identifier, TokenBucket tokenBucket) {
        System.out.println("Adding to cache");
        map.put(identifier, tokenBucket);
    }
    
    
}
