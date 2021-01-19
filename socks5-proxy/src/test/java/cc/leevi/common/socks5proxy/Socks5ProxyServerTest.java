package cc.leevi.common.socks5proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Socks5ProxyServerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void startServer() throws IOException {
        Socks5ProxyServer socks5ProxyServer = new Socks5ProxyServer();
        socks5ProxyServer.startServer();
        System.in.read();
    }

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    @Test
    public void decode() {
        byte[] bytes = {-1, -74, -11, 0x06};
        System.out.println(bytesToHexFun2(bytes));
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(bytes);
        System.out.println(byteBuf.getShort(0) + "," +
                        byteBuf.getShortLE(0) + "," +
                        byteBuf.getUnsignedShort(0) + "," +
                        byteBuf.getUnsignedShortLE(0) + "," +
                        byteBuf.getInt(0) + "," +
                        byteBuf.getIntLE(0) + "," +
                        byteBuf.getUnsignedInt(0)+","+
                        byteBuf.getUnsignedIntLE(0)+","+
                        byteBuf.getMedium(0) + "," +
                        byteBuf.getMediumLE(0) + "," +
                        byteBuf.getUnsignedMedium(0) + "," +
                        byteBuf.getUnsignedMediumLE(0) + ","+
                        byteBuf.getLong(0)+","+
                        byteBuf.getLongLE(0));

        int d = 14506879;
        System.out.println(bytesToHexFun2(Util.intToByteArray(d)));
    }

    public static String bytesToHexFun2(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for(byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }

        return new String(buf);
    }
}
