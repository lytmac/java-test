package jdk_base.nio.channel;

import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelTransferTest {

    public static void main(String[] args) throws Exception {
        ReadableByteChannel src = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);

        doBlockTransfer(src, dest);
        doNonBlockTransfer(src, dest);

        src.close();
        dest.close();
    }

    private static void doBlockTransfer(ReadableByteChannel src, WritableByteChannel dest) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(30);

        while (src.read(byteBuffer) != -1) { //输入未结束
            byteBuffer.flip(); //开始写入
            dest.write(byteBuffer);
            byteBuffer.compact();
        }

        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) { //输入结束，可能还有数据留存在缓存里
            dest.write(byteBuffer);
        }
    }

    private static void doNonBlockTransfer(ReadableByteChannel src, WritableByteChannel dest) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(30);
        while (src.read(byteBuffer) != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining())
                dest.write(byteBuffer);

            byteBuffer.clear();
        }
    }

}
