package jdk_test.jdk_base.nio.channel;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelPositionTest {

    private static final String PATH = "/Users/cdliuyang25/Code/Git/java-test/out/test/java-test/jdk_test/jdk_base/nio/test_file/out";

    public static void main(String[] args) throws Exception {
        RandomAccessFile file1 = new RandomAccessFile(new File(PATH), "rw");
        RandomAccessFile file2 = new RandomAccessFile(new File(PATH), "rw");

        //在同一个RandomAccessFile对象上多次调用getChannel()获取渠道，返回的应该是同一个渠道。
        // 原代码只new了一个RandomAccessFile对象，获取channel1和channel2，线程2中channel2.close()方法会关闭channel1
        final FileChannel channel1 = file1.getChannel();
        final FileChannel channel2 = file2.getChannel();
        final ByteBuffer buffer1 = ByteBuffer.allocateDirect(20);
        final ByteBuffer buffer2 = ByteBuffer.allocateDirect(20);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " begin running!");
                    while (channel1.read(buffer1) != -1) { //read方法需要拿锁，会阻塞其他线程执行read方法
                        buffer1.flip();
                        while (buffer1.hasRemaining()) {
                            System.out.println(Thread.currentThread().getName() + ": " + (char) buffer1.get());
                        }
                        buffer1.clear();
                    }
                    channel1.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "thread-1");

        t1.start();


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " begin running!");
                    while (channel2.read(buffer2) != -1) {
                        buffer2.flip();
                        while (buffer2.hasRemaining()) {
                            System.out.println(Thread.currentThread().getName() + ": " + (char) buffer2.get());
                        }
                        buffer2.clear();
                    }

                    channel2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "thread-2");
        t2.start();

        Thread.sleep(20000);
//        file.close();
    }
}
