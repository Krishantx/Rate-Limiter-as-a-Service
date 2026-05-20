package com.github.krishantx.RLaaS.Repo;

import java.util.HashMap;

public class TempRepo {
    HashMap<String, String> map;

    public TempRepo() {
        map = new HashMap<>();
    }

    public String getIdentifier(String identifier) {
        return map.get(identifier);
    }
    public void putObject(String identifier, String object) {
        map.put(identifier, object);
    }
    
    
}
