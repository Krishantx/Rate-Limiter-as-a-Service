package com.github.krishantx.Model;

public class TokenBucket {
    private int tokens;
    private long timestamp;

    public TokenBucket(){}

    public TokenBucket(int tokens, long timestamp) {
        this.tokens = tokens;
        this.timestamp = timestamp;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return tokens + " : " + timestamp;
    }
}
