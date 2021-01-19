package com.example.datacollector.rpc;


import java.nio.charset.StandardCharsets;

public class DefaultRequestParamValue {

    private final static byte startByte = 0x12;

    private String value;

    public DefaultRequestParamValue(String value) {
        this.value = value;
    }

    public static DefaultRequestParamValue of(String value) {
        return new DefaultRequestParamValue(value);
    }

    public String getValue() {
        return value;
    }

    public byte[] encode() {
        byte[] nameBytes = value.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[1 + 1 + nameBytes.length];
        result[0] = startByte;
        result[1] = (byte) nameBytes.length;
        System.arraycopy(nameBytes, 0, result, 2, nameBytes.length);
        return result;
    }
}
