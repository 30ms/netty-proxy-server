package com.example.datacollector.rpc.request;

import com.example.datacollector.rpc.DefaultRequest;
import com.example.datacollector.rpc.MethodName;
import com.example.datacollector.rpc.UserToken;
import com.example.datacollector.rpc.protobuf.LoginRequestProto;
import com.google.protobuf.Message;

import java.util.List;

public class LoginRequest extends DefaultRequest {

    //00 00 27 11 00 0f 42 a5
    private static final byte[] fixedBytes = {0x00, 0x00, 0x27, 0x11, 0x00, 0x0f, 0x42, -91};

    private static final MethodName methodName = MethodName.LOGIN;


    public LoginRequest(UserToken userToken, LoginRequestProto.LoginRequestMessage requestMessage) {
        super(userToken, fixedBytes, methodName, requestMessage);
    }
}
