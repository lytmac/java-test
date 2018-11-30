package jdk_base.nio.buffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static java.nio.channels.FileChannel.MapMode.PRIVATE;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.channels.FileChannel.MapMode.READ_WRITE;

/**
 * PRIVATE映射区的改动不会同步到原文件中，也无法感知到文件的其他改动
 * NIO内存映射文件会对内存和文件进行同步，同步机制暂不清楚
 */
public class MapFile {
    public static void main(String[] argv) throws Exception {
        File tempFile = File.createTempFile("map_file_test", null);
        RandomAccessFile file = new RandomAccessFile(tempFile, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer tempBuffer = ByteBuffer.allocateDirect(100000);

        /*===================================初始数据准备=======================================*/
        // 第一次写入文件
        tempBuffer.put("This is the file content".getBytes());
        tempBuffer.flip();
        channel.write(tempBuffer, 0);

        // 第二次写入文件，从文件的8192(即8K:文件映射到内存的单位页的大小)的位置开始，会写入文件的第二页。
        tempBuffer.clear();
        tempBuffer.put("This is more file content".getBytes());
        tempBuffer.flip();
        channel.write(tempBuffer, 8192);
        /*====================================================================================*/

        // 创建三种不同的映射Map，同时指向同一文件
        MappedByteBuffer readOnlyMap = channel.map(READ_ONLY, 0, channel.size());
        MappedByteBuffer readWriteMap = channel.map(READ_WRITE, 0, channel.size());
        MappedByteBuffer copyOnWriteMap = channel.map(PRIVATE, 0, channel.size());

        //打印映射区原始信息
        System.out.println("original Buffer Info:");
        dumpBuffer("READ_ONLY", readOnlyMap);
        dumpBuffer("READ_WRITE", readWriteMap);
        dumpBuffer("COPY_ON_WRITE", copyOnWriteMap);
        System.out.println("=====================================================\n");

        // 修改copy-on-write映射区: 只有copy-on-write映射区感知到改动
        copyOnWriteMap.position(0); //在首字符开始写入
        copyOnWriteMap.put("COW".getBytes()); //只是对copy的副本做了修改，改动没有同步到文件里
        System.out.println("Change To Copy On Write Buffer");
        dumpBuffer("READ_ONLY", readOnlyMap);
        dumpBuffer("READ_WRITE", readWriteMap);
        dumpBuffer("COPY_ON_WRITE", copyOnWriteMap);
        System.out.println("=====================================================\n");

        // 修改read & write映射区: read-only映射区和read-and-write映射区都感知到改动，copy-on-write映射区未感知到改动
        readWriteMap.position(9); //避免覆盖首次修改
        readWriteMap.put(" R/W ".getBytes());
        readWriteMap.position(8194);
        readWriteMap.put(" R/W ".getBytes());
        System.out.println("Change To Read & Write Buffer");
        dumpBuffer("READ_ONLY", readOnlyMap);
        dumpBuffer("READ_WRITE", readWriteMap);
        dumpBuffer("COPY_ON_WRITE", copyOnWriteMap);
        System.out.println("=====================================================\n");

        // 打印出第一次改动后的文件:read-and-write映射区的改动同步到文件中，copy-on-write映射区的改动未同步到文件中
        tempBuffer.clear();
        channel.read(tempBuffer);
        System.out.println("The Origin File After First Modify");
        dumpBuffer("after file first modify", tempBuffer);
        System.out.println("=====================================================\n");

        // 通过别的通道将数据写入文件:只有copy-on-write映射区未感知到改动
        tempBuffer.clear();
        tempBuffer.put("Channel other ".getBytes());
        tempBuffer.flip();
        channel.write(tempBuffer, 0);
        tempBuffer.rewind();
        channel.write(tempBuffer, 8202);
        System.out.println("Write On Other Channel");
        dumpBuffer("READ_ONLY", readOnlyMap);
        dumpBuffer("READ_WRITE", readWriteMap);
        dumpBuffer("COPY_ON_WRITE", copyOnWriteMap);
        System.out.println("=====================================================\n");

        // 再次修改copy-on-write映射区
        copyOnWriteMap.position(8207);
        copyOnWriteMap.put(" COW2 ".getBytes());
        System.out.println("Second Change To Copy On Write Buffer");
        dumpBuffer("READ_ONLY", readOnlyMap);
        dumpBuffer("READ_WRITE", readWriteMap);
        dumpBuffer("COPY_ON_WRITE", copyOnWriteMap);
        System.out.println("=====================================================\n");

        // 再次修改read & write映射区
        readWriteMap.position(0);
        readWriteMap.put(" R/W2 ".getBytes());
        readWriteMap.position(8210);
        readWriteMap.put(" R/W2 ".getBytes());
        readWriteMap.force();
        System.out.println("Second change to R/W buffer");
        dumpBuffer("READ_ONLY", readOnlyMap);
        dumpBuffer("READ_WRITE", readWriteMap);
        dumpBuffer("COPY_ON_WRITE", copyOnWriteMap);
        System.out.println("=====================================================\n");

        channel.close();
        file.close();
        tempFile.delete();
    }


    // 打印映射区内容,计数及跳过空字符
    private static void dumpBuffer(String prefix, ByteBuffer buffer) {
        System.out.print(prefix + ": '");
        int nulls = 0;
        int limit = buffer.limit();
        for (int i = 0; i < limit; i++) {
            char c = (char) buffer.get(i);
            if (c == '\u0000') {
                nulls++;
                continue;
            }
            if (nulls != 0) {
                System.out.print("|[" + nulls + " nulls]|");
                nulls = 0;
            }
            System.out.print(c);
        }
        System.out.println("'");
    }
}
