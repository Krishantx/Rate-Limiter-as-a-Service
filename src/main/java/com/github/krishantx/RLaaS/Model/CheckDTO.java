package com.github.krishantx.RLaaS.Model;


public class CheckDTO {
    private String identifier;
    public CheckDTO(String identifier) {
        this.identifier = identifier;
    }
    @Override
    public String toString() {
        return identifier;
    }
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
