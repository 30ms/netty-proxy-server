package com.example.datacollector.restapi;

import com.asprise.ocr.Ocr;
import com.example.datacollector.config.RPCRequestTemplate;
import com.example.datacollector.rpc.DefaultRequest;
import com.example.datacollector.rpc.UserToken;
import com.example.datacollector.config.UserTokenStorage;
import com.example.datacollector.rpc.protobuf.LoginRequestProto;
import com.example.datacollector.rpc.protobuf.LoginResponseProto;
import com.example.datacollector.rpc.request.LoginRequest;
import com.example.datacollector.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserTokenStorage userTokenStorage;

    @GetMapping("/login")
    public ResponseEntity login() throws Exception {
        String userToken = loginService.login();
        userTokenStorage.set(userToken);
        return ResponseEntity.ok(userToken);
    }

}
