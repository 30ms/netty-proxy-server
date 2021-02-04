package com.example.datacollector.config;

import com.example.datacollector.rpc.UserToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserTokenStorage {

    private UserToken userToken = null;

    public UserToken get() {
        if (userToken == null|| !StringUtils.hasLength(userToken.getToken())) {
            throw new RuntimeException("token is not exist, please login!");
        }
        return userToken;
    }

    public void set(String token) {
        userToken = new UserToken(token);
    }

    public void remove() {
        this.userToken = null;
    }
}
