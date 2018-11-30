package jdk_base.nio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelWriteReadTest {

    private static final String READ_PATH = "/Users/cdliuyang25/Code/Git/java-test/out/test/java-test/jdk_test/jdk_base/nio/test_file/out";
    private static final String WRITE_PATH = "/Users/cdliuyang25/Code/Git/java-test/out/test/java-test/jdk_test/jdk_base/nio/test_file/in";

    public static void main(String[] args) throws Exception {
        readTest();
        writeTest();
    }

    private static void readTest() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(READ_PATH, "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(20);

        System.out.println(channel.read(buffer));

        int count;
        while ((count = channel.read(buffer)) != -1) {
            System.out.println("the count is: " + count); //填满缓冲区或文件读到最后一个字节，方法即返回。
            buffer.flip(); //做一次读写翻转
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }

            System.out.println("=====================");
            buffer.clear(); //这里必须将position进行复位，否则缓存区一直为满，无法读入任何字节，channel.read()返回0
        }

        channel.close();
        randomAccessFile.close();
    }

    private static void writeTest() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(WRITE_PATH, "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(20);

        buffer.put(0, (byte) 'h').put(1, (byte) 'e').put(2, (byte) 'l').put(3, (byte) 'l').put(4, (byte) 'o')
                .put(5, (byte) 'h').put(6, (byte) 'e').put(7, (byte) 'l').put(8, (byte) 'l').put(9, (byte) 'o')
                .put(10, (byte) 'h').put(11, (byte) 'e').put(12, (byte) 'l').put(13, (byte) 'l').put(14, (byte) 'o')
                .put(15, (byte) 'h').put(16, (byte) 'e').put(17, (byte) 'l').put(18, (byte) 'l').put(19, (byte) 'o');

        buffer.clear();

        int count;
        while ((count = channel.write(buffer)) > 0) {
            System.out.println("the count is： " + count);
        }

        channel.close();
        randomAccessFile.close();
    }
}
