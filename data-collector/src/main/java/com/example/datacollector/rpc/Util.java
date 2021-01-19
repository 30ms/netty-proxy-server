package com.example.datacollector.rpc;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class Util {

    public static void writeByteBufToFile(ByteBuf byteBuf, String pathName, boolean append) {
        File file = new File(pathName);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new RuntimeException("创建目录失败! ");
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(pathName, append)) {
            byteBuf.markReaderIndex();
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            byteBuf.resetReaderIndex();
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static Field mappingClassField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("无法映射到类的属性");
        }
    }
}
