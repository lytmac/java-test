package jdk_test.jdk_base.nio;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class CopyFile {
//    static public void main(String args[]) throws Exception {
//        if (args.length < 2) {
//            System.err.println("Usage: java CopyFile infile outfile");
//            System.exit(1);
//        }
//
//        String inFile = args[0];
//        String outFile = args[1];
//
//        FileInputStream inputStream = new FileInputStream(inFile);
//        FileOutputStream outputStream = new FileOutputStream(outFile);
//
//        FileChannel inputStreamChannel = inputStream.getChannel();
//        FileChannel outputStreamChannel = outputStream.getChannel();
//
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//
//        while (true) {
//            buffer.clear();
//            int r = inputStreamChannel.read(buffer);
//            if (r == -1) {
//                break;
//            }
//            buffer.flip();
//            outputStreamChannel.write(buffer);
//        }
//    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java CopyFile infile outfile");
            return;
        }

        String filePath = args[0];

        FileInputStream inputStream = new FileInputStream(filePath);

        final FileChannel inputChannel = inputStream.getChannel();

        final ByteBuffer buffer = ByteBuffer.allocate(10);

        Thread inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (inputChannel.read(buffer) != -1) {
                            buffer.clear();
                            System.out.println("=============start================");
                            if (buffer.hasRemaining()) {
                                System.out.println(buffer.get());
                            }
                            buffer.flip();
                        } else {
                            System.out.println("==============end=================");
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        inputThread.start();
    }
}
