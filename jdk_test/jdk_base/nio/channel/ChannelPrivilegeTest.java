package jdk_test.jdk_base.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelPrivilegeTest {

    private static final String PATH = "/Users/cdliuyang25/Code/Git/java-test/out/test/java-test/jdk_test/jdk_base/nio/test_file/out";

    public static void main(String[] args) {
        try {
            File file = new File(PATH);
            FileInputStream fis = new FileInputStream(file);
            FileChannel fileChannel = fis.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(20);

            /**
             * write(ByteBuffer)会抛出异常:java.nio.channels.NonWritableChannelException。
             * 因为FileInputStream对象总是以read-only的权限打开文件。
             * 所以FileInputStream对象上获取的channel对象不具备写权限。
             */
            while (fileChannel.write(byteBuffer) > 0) {
                System.out.println("the channel write to buffer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
