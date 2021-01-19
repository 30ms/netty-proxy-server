package cc.leevi.common.socks5proxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
}
