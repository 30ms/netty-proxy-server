package com.example.datacollector.rpc;

import com.google.protobuf.Message;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DefaultRequest {

    protected final UserToken userToken;

    protected final byte[] fixedBytes;

    protected final MethodName methodName;

    protected final Message requestMessage;

    public DefaultRequest(UserToken userToken, byte[] fixedBytes, MethodName methodName, Message requestMessage) {
        this.userToken = userToken;
        this.fixedBytes = fixedBytes;
        this.methodName = methodName;
        this.requestMessage = requestMessage;
    }

    public byte[] encode() {
        byte[] tokenBytes = userToken.encode();
        byte[] methodNameBytes = methodName.encode();
        byte[] requestMessageBytes = requestMessage.toByteArray();
        int dataLength = tokenBytes.length + fixedBytes.length + methodNameBytes.length + requestMessageBytes.length;
        byte[] result = new byte[4 + tokenBytes.length + fixedBytes.length + methodNameBytes.length + requestMessageBytes.length];
        int pos = 0;
        System.arraycopy(Util.intToByteArray(dataLength), 0, result, pos, 4);
        pos += 4;
        System.arraycopy(tokenBytes, 0, result, pos, tokenBytes.length);
        pos += tokenBytes.length;
        System.arraycopy(fixedBytes, 0, result, pos, fixedBytes.length);
        pos += fixedBytes.length;
        System.arraycopy(methodNameBytes, 0, result, pos, methodNameBytes.length);
        pos += methodNameBytes.length;
        System.arraycopy(requestMessageBytes, 0, result, pos, requestMessageBytes.length);
        return result;
    }
}
