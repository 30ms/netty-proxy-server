package com.example.datacollector.rpc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DefaultRequest {

    protected final String userToken;

    protected final byte[] fixedBytes;

    protected final MethodName methodName;

    protected final List<DefaultRequestParam> params;

    public DefaultRequest(String userToken, byte[] fixedBytes, MethodName methodName, List<DefaultRequestParam> params) {
        this.userToken = userToken;
        this.fixedBytes = fixedBytes;
        this.methodName = methodName;
        this.params = params;
    }

    public byte[] encode() {
        byte[] tokenBytes = userToken.getBytes(StandardCharsets.UTF_8);
        byte[] methodNameBytes = methodName.encode();
        List<byte[]> paramByteAList = params.stream().map(DefaultRequestParam::encode).collect(Collectors.toList());
        int totalLength = paramByteAList.stream().mapToInt(byteArray -> byteArray.length).reduce(0, Integer::sum);
        byte[] paramByteA = new byte[totalLength];
        int pos = 0;
        for (byte[] byteA : paramByteAList) {
            System.arraycopy(byteA, 0, paramByteA, pos, byteA.length);
            pos += byteA.length;
        }
        int dataLength = tokenBytes.length + fixedBytes.length + methodNameBytes.length + paramByteA.length;
        byte[] result = new byte[dataLength];
        int resultPos = 0;
        System.arraycopy(tokenBytes, 0, result, resultPos, tokenBytes.length);
        resultPos += tokenBytes.length;
        System.arraycopy(fixedBytes, 0, result, resultPos, fixedBytes.length);
        resultPos += fixedBytes.length;
        System.arraycopy(methodNameBytes, 0, result, resultPos, methodNameBytes.length);
        resultPos += methodNameBytes.length;
        System.arraycopy(paramByteA, 0, result, resultPos, paramByteA.length);
        return result;
    }
}
