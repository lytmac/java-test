package jdk_test.jdk_base.io;

import java.io.File;
import java.io.FileInputStream;

public class IOTest {

    private static final String PATH = "/Users/cdliuyang25/Code/Git/java-test/out/test/java-test/jdk_test/jdk_base/nio/test_file/out";


    public static void main(String[] args) throws Exception{
        File file = new File(PATH);

        final FileInputStream fis1 = new FileInputStream(file);
        final FileInputStream fis2 = new FileInputStream(file);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] buffer = new byte[200];
                    while (fis1.read(buffer) != -1) {

                        for(byte b : buffer) {
                            System.out.println(Thread.currentThread().getName() + ": "+ b);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "thread-1").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] buffer = new byte[200];
                    while (fis2.read(buffer) != -1) {

                        for(byte b : buffer) {
                            System.out.println(Thread.currentThread().getName() + ": "+ b);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "thread-2").start();
    }
}
