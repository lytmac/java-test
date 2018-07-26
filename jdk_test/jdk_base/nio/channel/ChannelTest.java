package jdk_test.jdk_base.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelTest {

    public static void main(String[] args) {
        try {
            File file = new File("/Users/cdliuyang25/Downloads/ioExample/select_client.c");
            file.setReadOnly();

            FileInputStream input = new FileInputStream(file);

            FileChannel fileChannel = input.getChannel();

            fileChannel.write(ByteBuffer.allocate(2000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
