package com.example.datacollector.rpc;


import java.nio.charset.StandardCharsets;

public class DefaultRequestParamName {

    private final static byte startByte = 0x0a;

    private String name;

    public DefaultRequestParamName(String name) {
        this.name = name;
    }

    public static DefaultRequestParamName of(String name) {
        return new DefaultRequestParamName(name);
    }

    public String getName() {
        return name;
    }

    public byte[] encode() {
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[1 + 1 + nameBytes.length];
        result[0] = startByte;
        result[1] = (byte) nameBytes.length;
        System.arraycopy(nameBytes, 0, result, 2, nameBytes.length);
        return result;
    }
}
