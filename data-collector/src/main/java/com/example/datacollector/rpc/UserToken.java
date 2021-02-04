package com.example.datacollector.rpc;

import java.nio.charset.StandardCharsets;

public class UserToken {

    private final String token;

    public UserToken(String token) {
        this.token = token;
    }

    public byte[] encode() {
        byte[] result = new byte[32];
        byte[] t = token.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(t, 0, result, 0, t.length);
        return result;
    }

    public String getToken() {
        return token;
    }
}
