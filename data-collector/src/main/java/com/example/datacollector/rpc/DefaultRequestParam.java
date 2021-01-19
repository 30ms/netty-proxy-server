package com.example.datacollector.rpc;

public class DefaultRequestParam {

    private final static byte startByte = 0x0a;

    private DefaultRequestParamName paramName;

    private DefaultRequestParamValue paramValue;

    public DefaultRequestParam(DefaultRequestParamName paramName, DefaultRequestParamValue paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public static DefaultRequestParam of(DefaultRequestParamName paramName, DefaultRequestParamValue paramValue) {
        return new DefaultRequestParam(paramName, paramValue);
    }

    public DefaultRequestParamName getParamName() {
        return paramName;
    }

    public DefaultRequestParamValue getParamValue() {
        return paramValue;
    }

    public byte[] encode() {
        byte[] paramNameBytes = getParamName().encode();
        byte[] paramValueBytes = getParamValue().encode();
        byte dataLength = (byte) (paramNameBytes.length + paramValueBytes.length);
        byte[] result = new byte[1 + 1 + paramNameBytes.length + paramValueBytes.length];
        result[0] = startByte;
        result[1] = dataLength;
        System.arraycopy(paramNameBytes, 0, result, 2, paramNameBytes.length);
        System.arraycopy(paramValueBytes, 0, result, 2 + paramNameBytes.length, paramValueBytes.length);
        return result;
    }
}
