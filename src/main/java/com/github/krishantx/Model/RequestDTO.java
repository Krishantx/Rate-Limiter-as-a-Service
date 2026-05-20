package com.github.krishantx.Model;


public class RequestDTO {
    private String identifier;
    public RequestDTO(String identifier) {
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
