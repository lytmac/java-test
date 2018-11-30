package jdk_base.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelRWTest {

    private static final String PATH = "/Users/cdliuyang25/Code/Git/java-test/out/test/java-test/jdk_test/jdk_base/nio/test_file/rw";


    public static void main(String[] args) throws Exception {
        writeTest();
        readTest();
    }

    /**
     * 写文件可以一次性从buffer中读取全部字节
     * @throws Exception
     */
    static void  writeTest() throws Exception{
        ByteBuffer buffer = ByteBuffer.allocateDirect(100000);

        //先填充ByteBuffer
        for (int index = 0; index < 100000; index++) {
            buffer.put(index, (byte) index);
        }

        File file = new File(PATH);
        FileOutputStream stream = new FileOutputStream(file);
        FileChannel channel  = stream.getChannel();

        int count = 0;
        while(buffer.hasRemaining()) {
            channel.write(buffer);
            System.out.println("the count is: " + count);
            count++;
        }

        channel.close();
        stream.close();
    }

    /**
     * 当buffer容量大于读取的文件长度时，一次就读完了
     * @throws Exception
     */
    static  void readTest() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocateDirect(200000);
        System.out.println("the position is: " + buffer.position());
        System.out.println("the limit is: " + buffer.limit());

        File file = new File(PATH);

        FileInputStream stream = new FileInputStream(file);
        FileChannel channel = stream.getChannel();

        int count = 0;
        int num;
        while((num = channel.read(buffer)) != -1) {
            System.out.println("the count is: " + count);
            System.out.println("the num is: " + num);

            System.out.println("the position after read is: " + buffer.position());

            count++;
        }

        channel.close();
        stream.close();
    }
}
