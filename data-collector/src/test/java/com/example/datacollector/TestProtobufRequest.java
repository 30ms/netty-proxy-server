package com.example.datacollector;

import com.example.datacollector.rpc.MethodName;
import com.example.datacollector.rpc.UserToken;
import com.google.protobuf.Message;


public class TestProtobufRequest extends TestDefaultRequest {
    private static final byte[] fixedBytes = {0x00, 0x00, 0x0b, -72, 0x00, 0x2d, -56, 0x52};

    private static final MethodName methodName=MethodName.SALE_ORDER_LIST;


    public TestProtobufRequest(UserToken userToken, Message requestMessage) {
        super(userToken, fixedBytes, methodName, requestMessage);
    }
}
