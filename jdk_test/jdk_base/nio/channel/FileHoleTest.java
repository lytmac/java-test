package jdk_test.jdk_base.nio.channel;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileHoleTest {
    private static final String PATH = "/Users/cdliuyang25/Code/Git/java-test/out/test/java-test/jdk_test/jdk_base/nio/test";

    public static void main(String[] args) throws Exception {
        File temp = File.createTempFile(PATH, null);
        RandomAccessFile file = new RandomAccessFile(temp, "rw");
        FileChannel channel = file.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        //几次写入中间的间隔以0填充
        writeData(0, byteBuffer, channel);
        writeData(500, byteBuffer, channel);
        writeData(1000, byteBuffer, channel);

        System.out.println("write temp file" + temp.getPath() + ". size is: " + channel.size());
        System.out.println("=================================================================");

        byteBuffer.clear();
        channel.read(byteBuffer);
        while(byteBuffer.hasRemaining()) {
            System.out.print(byteBuffer.get());
        }

        channel.close();
        file.close();

    }

    private static void writeData(int position, ByteBuffer byteBuffer, FileChannel channel) throws Exception {
        String str = "*<-----location" + position;

        byteBuffer.clear();
        byteBuffer.put(str.getBytes("US-ASCII"));
        byteBuffer.flip();
        channel.position(position);
        channel.write(byteBuffer);
    }
}
