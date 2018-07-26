package jdk_test.jdk_base.nio.buffer;


import java.nio.ByteBuffer;

/**
 * 1. 直接缓存的读写性能更高
 * 2. 直接缓存的分配开销更大
 */
public class BufferPerformanceTest {
    
    // 分配堆内存
    private static void bufferAccess() {
        ByteBuffer b = ByteBuffer.allocate(500); //非直接缓存
        
        long startTime = System.currentTimeMillis();
        for (int index = 0; index < 1000000; index++) {
            for (int putNum = 0; putNum < 99; putNum++)
                b.putInt(putNum);
            b.flip(); //切换读写模式
            for (int getNum = 0; getNum < 99; getNum++)
                b.getInt();
            b.clear();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("put_get_non_direct_buffer:" + (endTime - startTime));
    }

    // 分配直接内存
    private static void directBufferAccess() {
        ByteBuffer b = ByteBuffer.allocateDirect(500);
        
        long startTime = System.currentTimeMillis();
        for (int index = 0; index < 1000000; index++) {
            for (int putNum = 0; putNum < 99; putNum++)
                b.putInt(putNum);
            b.flip();
            for (int getNum = 0; getNum < 99; getNum++)
                b.getInt();
            b.clear();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("put_get_direct_buffer:" + (endTime - startTime));
    }

    private static void bufferAllocate() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            ByteBuffer.allocate(1000);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("allocate_non_direct_buffer:" + (endTime - startTime));
    }

    private static void directBufferAllocate() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            ByteBuffer.allocateDirect(1000);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("allocate_direct_buffer:" + (endTime - startTime));
    }

    public static void main(String args[]) {
        System.out.println("读写性能测试：");
        bufferAccess();
        directBufferAccess();

        System.out.println("========================================");

        System.out.println("分配性能测试：");
        bufferAllocate();
        directBufferAllocate();
    }
}
