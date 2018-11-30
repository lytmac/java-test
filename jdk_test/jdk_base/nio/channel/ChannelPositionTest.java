package jdk_base.nio.channel;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelPositionTest {

    private static final String PATH = "/Users/cdliuyang25/Code/Git/java-test/out/test/java-test/jdk_test/jdk_base/nio/test_file/out";

    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile(new File(PATH), "rw");

        // 在同一个RandomAccessFile对象上多次调用getChannel()获取渠道，返回的应该是同一个渠道。
        // 原代码只new了一个RandomAccessFile对象，获取channel1和channel2，线程2中channel2.close()方法会关闭channel1
        final FileChannel channel = file.getChannel();
        final ByteBuffer bufferOne = ByteBuffer.allocateDirect(20);
        final ByteBuffer bufferTwo = ByteBuffer.allocateDirect(20);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " begin running!");
                    while (channel.read(bufferOne) != -1) { //read方法需要拿锁，会阻塞其他线程执行read方法
                        System.out.println("the current thread is: " + Thread.currentThread().getName() + ".the current position of file is: " + channel.position());
                        bufferOne.flip();
                        while (bufferOne.hasRemaining()) {
                            System.out.println(Thread.currentThread().getName() + ": " + (char) bufferOne.get());
                        }
                        bufferOne.clear();
                    }
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
                    while (channel.read(bufferTwo) != -1) {
                        System.out.println("the current thread is: " + Thread.currentThread().getName() + ".the current position of file is: " + channel.position());
                        bufferTwo.flip();
                        while (bufferTwo.hasRemaining()) {
                            System.out.println(Thread.currentThread().getName() + ": " + (char) bufferTwo.get());
                        }
                        bufferTwo.clear();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "thread-2");
        t2.start();

        Thread.sleep(20000);
        channel.close();
        file.close();
    }
}
