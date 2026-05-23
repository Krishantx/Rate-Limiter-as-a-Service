package com.github.krishantx.Model;

public class TokenBucket {
    private float tokens;
    private long timestamp;

    public TokenBucket(){}

    public TokenBucket(float tokens, long timestamp) {
        this.tokens = tokens;
        this.timestamp = timestamp;
    }

    public float getTokens() {
        return tokens;
    }

    public void setTokens(float tokens) {
        this.tokens = tokens;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Tokens : " + tokens + " \nTimestamp " + timestamp;
    }
}
